package com.young.cache.mapper

import com.young.cache.model.*
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.*
import com.young.domain.model.Header
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform

object DataToDomainMapper {
    fun DataFullRouteInformationBody.DataToDomain() : DomainFullRouteInformationBody =
        BaseMapper<DataFullRouteInformationBody , DomainFullRouteInformationBody>().run {
            this(this@DataToDomain)
        }

    fun DataConvenienceInformation.DataToDomain(): DomainConvenienceInformation {
        val convenienceMapper =
            BaseMapper(DataConvenienceInformation::class, DomainConvenienceInformation::class)
        val headerMapper = BaseMapper(Header::class, Header::class)
        val bodyMapper = BaseMapper(
            DataConvenienceInformationBody::class,
            DomainConvenienceInformationBody::class
        )

        convenienceMapper.apply {
            register("header", headerMapper)
            register("body", BaseMapper.setList(bodyMapper))
        }.run {
            return this(this@DataToDomain)
        }
    }

    fun DataPlatformEntrance.DataToDomain(): DomainPlatformEntrance {
        val mapper = BaseMapper<DataPlatformEntrance, DomainPlatformEntrance>()
        val body = BaseMapper<DataPlatformEntranceBody, DomainPlatformEntranceBody>()
        val header = BaseMapper(Header::class, Header::class)

        mapper.apply {
            register("header", header)
            register("body", BaseMapper.setList(body))
        }.run {
            return this(this@DataToDomain)
        }
    }

    fun DataStationTelNumber.DataToDomain(): DomainStationTelNumber {
        val itemMapper = BaseMapper(DataStationItem::class, StationItem::class)
        val bodyMapper = BaseMapper(DataBody::class, Body::class).apply {
            register("items", BaseMapper.setList(itemMapper))
        }

        val headerMapper = BaseMapper(DataTelHeader::class, TelHeader::class)
        val responseMapper =
            BaseMapper(DataResponse::class, Response::class).apply {
                register("body", bodyMapper)
                register("header", headerMapper)
            }

        val mainMapper =
            BaseMapper(DataStationTelNumber::class, DomainStationTelNumber::class).apply {
                register("response", responseMapper)
            }

        return mainMapper(this)
    }

    fun DataAllStationCodes.DataToDomain(): DomainAllStationCodes {
        val rowMapper = BaseMapper<DataRow, DomainRow>()
        val resultMapper = BaseMapper<DataRESULT, DomainRESULT>()
        val serviceMapper =
            BaseMapper<DataSearchInfoBySubwayNameService, DomainSearchInfoBySubwayNameService>().apply {
                register("RESULT", resultMapper)
                register("row", BaseMapper.setList(rowMapper))
            }

        val mainMapper = BaseMapper<DataAllStationCodes, DomainAllStationCodes>().apply {
            register("SearchInfoBySubwayNameService", serviceMapper)
        }

        return mainMapper(this)
    }

    fun getLocalTime(time: String): String = StringBuilder().append(time)
        .insert(time.length - 4, ":")
        .insert(time.length - 1, ":")
        .toString()

    suspend fun DataStationTimeTable.DataToDomain(upDown: String): DomainStationTimeTable {
        return if (body.isNullOrEmpty()) DomainStationTimeTable(listOf(), "", "")
        else {
            val group = body.groupBy { it.orgStinCd < it.tmnStinCd }

            flowOf(group.getValue(upDown == "1"))
                .transform {
                    it.map {
                        getLocalTime(it.arvTm ?: it.dptTm ?: "")
                    }.run {
                        emit(
                            filter { it.substring(0..1).toInt() > 2 }.sorted() +
                                    filter { it.substring(0..1).toInt() <= 2 }.sorted()
                        )
                    }
                }.transform {
                    it.map {
                        it.dropLast(3)
                    }.run {
                        emit(
                            DomainStationTimeTable(this, first(), last())
                        )
                    }
                }.first()
        }
    }

    suspend fun DataStationSeoulTimeTable.DataToDomain(): DomainStationTimeTable {
        return if (SearchSTNTimeTableByIDService == null) DomainStationTimeTable(listOf(), "", "")
        else {
            flowOf(SearchSTNTimeTableByIDService.row)
                .transform {
                    it.map {
                        (it.ARRIVETIME).dropLast(3)
                    }.run {
                        emit(
                            DomainStationTimeTable(this, first(), last())
                        )
                    }
                }.first()
        }
    }
}