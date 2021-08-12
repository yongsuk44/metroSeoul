package com.young.presentation.mapper

import android.location.Location
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.*
import com.young.domain.model.ConvenienceInformationBody
import com.young.domain.model.PlatformEntranceBody
import com.young.domain.model.Row
import com.young.presentation.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DomainToUiMapper {

    fun Row.DomainToUi() : com.young.presentation.model.Row {
        return BaseMapper<Row , com.young.presentation.model.Row>().run { this(this@DomainToUi) }
    }

    fun getLocalTime(time: String): String = StringBuilder().append(time)
        .insert(time.length-4, ":")
        .insert(time.length-1, ":")
        .toString()


    suspend fun DomainTrailTimeTable.DomainToUi() : UiTrailTimeTable? {
        if (body.isNullOrEmpty()) return null
        else {
            val upTime = body!!.groupBy { it.orgStinCd }
            val downTime = body!!.groupBy { it.tmnStinCd }

            val bodyObject = body!!.map {
                com.young.presentation.model.TimeTableBody(
                    arvTm = it.arvTm,
                    lnCd = it.lnCd,
                    railOprIsttCd = it.railOprIsttCd,
                    stinCd = it.stinCd
                )
            }

            return flowOf(bodyObject)
                .transform {
                    it.map {
                        getLocalTime(it.arvTm)
                    }.run {
                        emit(filter { it.substring(0..1).toInt() > 1 })
                        emit(filter { it.substring(0..1).toInt() <= 1 })
                    }
                }
                .map {
                    val min = it.first().toString()
                    val max = it.last().toString()
                    UiTrailTimeTable(bodyObject , bodyObject , min , max)
                }.first()
        }
    }

    fun AllRouteInformation.DomainToUi(): com.young.presentation.model.IndexAllRouteInformation {

        val bodyMapper = BaseMapper(
            AllRouteInformation::class,
            com.young.presentation.model.IndexAllRouteInformation::class
        )

        bodyMapper.run {
            return this(this@DomainToUi)
        }
    }

    fun List<AllRouteInformation>.DomainToUi(): List<com.young.presentation.model.ListRouteInformation> {
        return this.groupBy {
            it.stinNm
        }.map {
            it.value.run {

                if (this.size >= 2) {
                    com.young.presentation.model.ListRouteInformation(
                        first().mreaWideCd,
                        this.map { it.railOprIsttCd },
                        first().routCd,
                        first().routNm,
                        this.map { it.lnCd },
                        this.map { it.stinCd },
                        first().stinConsOrdr,
                        first().stinNm,
                    )
                } else {
                    com.young.presentation.model.ListRouteInformation(
                        first().mreaWideCd,
                        listOf(first().railOprIsttCd),
                        first().routCd,
                        first().routNm,
                        listOf(first().lnCd),
                        listOf(first().stinCd),
                        first().stinConsOrdr,
                        first().stinNm,
                    )
                }

            }
        }
    }

    fun DomainConvenienceInformation.DomainToUi(): UiConvenienceInformation {
        val convenienceMapper = BaseMapper(
            DomainConvenienceInformation::class,
            com.young.presentation.model.UiConvenienceInformation::class
        )
        val headerMapper = BaseMapper(
            com.young.domain.model.Header::class,
            com.young.presentation.model.Header::class
        )
        val bodyMapper = BaseMapper(
            ConvenienceInformationBody::class,
            com.young.presentation.model.ConvenienceInformationBody::class
        )

        convenienceMapper.apply {
            register("header", headerMapper)
            register("body", com.young.domain.mapper.BaseMapper.setList(bodyMapper))
        }.run {
            return this(this@DomainToUi)
        }
    }

    fun UiStationNameAndMapXY.UiToDomain() : DomainStationNameAndMapXY {
        return DomainStationNameAndMapXY(
            stinCd = stinCd,
            stationName = stationName,
            mapX = mapX,
            mapY = mapY,
            trailCodeAndLineCode = DomainTrailCodeAndLineCode(trailCodeAndLineCode.railOprIsttCd,trailCodeAndLineCode.lnCd)
        )
    }

    fun DomainStationNameAndMapXY.DomainToUi() : UiStationNameAndMapXY {
        return UiStationNameAndMapXY(
            stinCd = stinCd,
            stationName = stationName,
            mapX = mapX,
            mapY = mapY,
            trailCodeAndLineCode = UiTrailCodeAndLineCode(trailCodeAndLineCode.railOprIsttCd,trailCodeAndLineCode.lnCd)
        )
    }

    fun List<DomainStationNameAndMapXY>.DomainToUiDistance(nowLocationLatitude: Double,nowLocationLongitude: Double) : List<UiStationNameDistance> {
        return groupBy {
            it.mapX to it.mapY
        }.map {
            it.value.run {
                if (size >= 2) {
                    UiStationNameDistance(
                        map { it.stinCd },
                        map { it.trailCodeAndLineCode.railOprIsttCd },
                        map { it.trailCodeAndLineCode.lnCd },
                        first().stationName,
                        getStartPosEndPosDistanceData(nowLocationLatitude,nowLocationLongitude,first().mapX, first().mapY)
                    )
                } else {
                    first().run {
                        UiStationNameDistance(
                            listOf(stinCd),
                            listOf(trailCodeAndLineCode.railOprIsttCd),
                            listOf(trailCodeAndLineCode.lnCd),
                            first().stationName,
                            getStartPosEndPosDistanceData(nowLocationLatitude,nowLocationLongitude,first().mapX, first().mapY)
                        )
                    }
                }
            }
        }
    }

    fun getStartPosEndPosDistanceData(nowLocationLatitude : Double , nowLocationLongitude : Double , x: Double, y: Double): Int {
        val start = Location("").apply {
            latitude = nowLocationLatitude
            longitude = nowLocationLongitude
        }

        val end = Location("").apply {
            latitude = x
            longitude = y
        }

        return start.distanceTo(end).toInt()
    }

    fun DomainPlatformEntrance.DomainToUi() : UiPlatformEntrance {
        val mapper = BaseMapper<DomainPlatformEntrance , UiPlatformEntrance>()
        val body = BaseMapper<PlatformEntranceBody, com.young.presentation.model.PlatformEntranceBody>()
        val header = BaseMapper(com.young.domain.model.Header::class, com.young.presentation.model.Header::class)

        mapper.apply {
            register("header" , header)
            register("body" ,BaseMapper.setList(body))
        }.run {
            return this(this@DomainToUi)
        }
    }
}