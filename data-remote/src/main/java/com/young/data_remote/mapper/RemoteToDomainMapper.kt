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
import com.young.data_remote.model.TimeTableBody
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.*

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
        val rowMapper = BaseMapper<Row , com.young.domain.model.Row>()
        val resultMapper = BaseMapper<RESULT , com.young.domain.model.RESULT>()
        val serviceMapper = BaseMapper<SearchInfoBySubwayNameService , com.young.domain.model.SearchInfoBySubwayNameService>().apply {
            register("RESULT" ,resultMapper)
            register("row" , BaseMapper.setList(rowMapper))
        }

        val mainMapper = BaseMapper<RemoteAllStationCodes , DomainAllStationCodes>().apply {
            register("SearchInfoBySubwayNameService" , serviceMapper)
        }

        return mainMapper(this)
    }

    fun RemoteStationTimeTable.RemoteToDomain() : DomainTrailTimeTable {
        val timeTableMapper =
            BaseMapper(RemoteStationTimeTable::class, DomainTrailTimeTable::class)
        val headerMapper = BaseMapper(Header::class, com.young.domain.model.Header::class)
        val timeTableBody =
            BaseMapper(TimeTableBody::class, com.young.domain.model.TimeTableBody::class)

        timeTableMapper.apply {
            register("header", headerMapper)
            register("body", BaseMapper.setList(timeTableBody))
        }.run {
            return this(this@RemoteToDomain)
        }
    }
}