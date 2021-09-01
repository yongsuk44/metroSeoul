package com.young.data.mapper

import com.young.base.BaseMapper
import com.young.data.model.*
import com.young.domain.model.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform

object DataToDomainMapper {
    fun DataLocationTrailData.DataToDomain() : DomainLocationTrailData {
        val mapper = BaseMapper<DataLocationTrailData , DomainLocationTrailData>()
        val body = BaseMapper<DataLocationData , DomainLocationData>()
        val headerMapper = BaseMapper<DataHeader , Header>()
        return mapper.apply {
            register("header" , headerMapper)
            register("body" , BaseMapper.setList(body))
        }.run {
            this(this@DataToDomain)
        }
    }

    fun DataStationNameAndMapXY.DataToDomain() : DomainStationNameAndMapXY {
        val mapper = BaseMapper<DataStationNameAndMapXY , DomainStationNameAndMapXY>()
        return mapper.apply {
            register("trailCodeAndLineCode" , BaseMapper<DataTrailCodeAndLineCode , DomainTrailCodeAndLineCode>())
        }.run {
            this(this@DataToDomain)
        }
    }

    fun DataTrailCodeAndLineCode.DataToDomain() : DomainTrailCodeAndLineCode =
        BaseMapper<DataTrailCodeAndLineCode , DomainTrailCodeAndLineCode>().run { this(this@DataToDomain) }

    fun DataFullRouteInformation.DataToDomain() : DomainFullRouteInformation {
        val mapper = BaseMapper<DataFullRouteInformation , DomainFullRouteInformation>()
        val headerMapper = BaseMapper<DataHeader , Header>()
        val bodyMapper = BaseMapper<DataFullRouteInformationBody , DomainFullRouteInformationBody>()

        return mapper.apply {
            register("header" , headerMapper)
            register("body" , BaseMapper.setList(bodyMapper))
        }.run {
            this(this@DataToDomain)
        }
    }

    fun DataRow.DataToDomain() : DomainRow = BaseMapper<DataRow , DomainRow>().run { this(this@DataToDomain) }

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

    fun DataStationBody.DataToDomain(): DomainStationBody = BaseMapper<DataStationBody , DomainStationBody>().run { this(this@DataToDomain) }

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

    suspend fun DataStationTimeTable.DataToDomain(upDown: String): DomainStationTimeTable? {
        return if (body.isNullOrEmpty()) null
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

    suspend fun DataStationSeoulTimeTable.DataToDomain(): DomainStationTimeTable? {
        return if (SearchSTNTimeTableByIDService == null) null
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