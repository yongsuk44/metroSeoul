package com.young.data_remote.mapper

import com.young.data_remote.model.*
import com.young.data_remote.model.Body
import com.young.data_remote.model.ConvenienceInformationBody
import com.young.data_remote.model.Header
import com.young.data_remote.model.PlatformEntranceBody
import com.young.data_remote.model.RESULT
import com.young.data_remote.model.Response
import com.young.data_remote.model.Row
import com.young.data_remote.model.SearchInfoBySubwayNameService
import com.young.data_remote.model.StationItem
import com.young.data_remote.model.TelHeader
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform
import java.lang.NullPointerException

object RemoteToDomainMapper {

    fun RemoteConvenienceInformation.RemoteToDomain(): DomainConvenienceInformation {
        val convenienceMapper =
            BaseMapper(RemoteConvenienceInformation::class, DomainConvenienceInformation::class)
        val headerMapper = BaseMapper(Header::class, com.young.domain.model.Header::class)
        val bodyMapper = BaseMapper(
            ConvenienceInformationBody::class,
            com.young.domain.model.ConvenienceInformationBody::class
        )

        convenienceMapper.apply {
            register("header", headerMapper)
            register("body", BaseMapper.setList(bodyMapper))
        }.run {
            return this(this@RemoteToDomain)
        }
    }

    fun RemotePlatformEntrance.RemoteToDomain(): DomainPlatformEntrance {
        val mapper = BaseMapper<RemotePlatformEntrance, DomainPlatformEntrance>()
        val body = BaseMapper<PlatformEntranceBody, com.young.domain.model.PlatformEntranceBody>()
        val header = BaseMapper(Header::class, com.young.domain.model.Header::class)

        mapper.apply {
            register("header", header)
            register("body", BaseMapper.setList(body))
        }.run {
            return this(this@RemoteToDomain)
        }
    }

    fun RemoteStationTelNumber.RemoteToDomain() : DomainStationTelNumber {
        val itemMapper = BaseMapper(StationItem::class , com.young.domain.model.StationItem::class)
        val bodyMapper = BaseMapper(Body::class, com.young.domain.model.Body::class).apply {
            register("items" , BaseMapper.setList(itemMapper))
        }

        val headerMapper = BaseMapper(TelHeader::class, com.young.domain.model.TelHeader::class)
        val responseMapper = BaseMapper(Response::class, com.young.domain.model.Response::class).apply {
            register("body" , bodyMapper)
            register("header" , headerMapper)
        }

        val mainMapper = BaseMapper(RemoteStationTelNumber::class, DomainStationTelNumber::class).apply {
            register("response" , responseMapper)
        }

        return mainMapper(this)
    }

    fun RemoteAllStationCodes.RemoteToDomain() : DomainAllStationCodes {
        val rowMapper = BaseMapper<Row, com.young.domain.model.Row>()
        val resultMapper = BaseMapper<RESULT, com.young.domain.model.RESULT>()
        val serviceMapper = BaseMapper<SearchInfoBySubwayNameService, com.young.domain.model.SearchInfoBySubwayNameService>().apply {
            register("RESULT" ,resultMapper)
            register("row" , BaseMapper.setList(rowMapper))
        }

        val mainMapper = BaseMapper<RemoteAllStationCodes , DomainAllStationCodes>().apply {
            register("SearchInfoBySubwayNameService" , serviceMapper)
        }

        return mainMapper(this)
    }

    fun getLocalTime(time: String): String = StringBuilder().append(time)
        .insert(time.length-4, ":")
        .insert(time.length-1, ":")
        .toString()

    suspend fun RemoteStationTimeTable.RemoteToDomain(upDown : String) : DomainStationTimeTable {
        if (body.isNullOrEmpty()) throw NullPointerException("Public APi Service 지하철 시간표를 가져오는 중 Body에 데이터가 없음 확인 필")
        else {
            val group = body.groupBy { it.orgStinCd < it.tmnStinCd }

            return flowOf(group.getValue(upDown == "1"))
                .transform {
                    it.map { getLocalTime(it.arvTm) }
                        .run {
                            emit(filter { it.substring(0..1).toInt() > 2 } + filter { it.substring(0..1).toInt() <= 2 })
                        }
                }.transform {
                    emit(
                        DomainStationTimeTable(it , it.first() , it.last() )
                    )
                }.first()
        }
    }

    suspend fun RemoteStationSeoulTimeTable.RemoteToDomain() : DomainStationTimeTable? {
        if (this.SearchSTNTimeTableByIDService == null) return null
        else {
            return flowOf(SearchSTNTimeTableByIDService.row)
                .transform {
                    val list = it.map { it.ARRIVETIME }
                    emit(
                        DomainStationTimeTable(list , list.first() , list.last())
                    )
                }.first()
        }
    }
}