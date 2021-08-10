package com.young.presentation.mapper

import android.location.Location
import android.os.Build
import androidx.annotation.RequiresApi
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.*
import com.young.domain.model.ConvenienceInformationBody
import com.young.domain.model.PlatformEntranceBody
import com.young.domain.model.TimeTableBody
import com.young.presentation.model.*
import java.lang.StringBuilder
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DomainToUiMapper {

    fun getLocalTime(time: String): String = StringBuilder().append(time)
        .delete(4,6)
        .insert(2, ":")
        .toString()


    fun DomainTrailTimeTable.DomainToUi() : UiTrailTimeTable {

        val bodyObject = body?.map {
            com.young.presentation.model.TimeTableBody(
                arvTm = getLocalTime(it.arvTm),
                lnCd = it.lnCd ,
                railOprIsttCd = it.railOprIsttCd ,
                stinCd = it.stinCd
            )
        }

//        val parsingLocalTime = body?.map {
//            LocalTime.parse(getLocalTime(it.arvTm) , DateTimeFormatter.ISO_LOCAL_TIME)
//        }?.groupBy {
//            it.hour == 0
//        }?.run {
//            MutableList<List<LocalTime>>(size) { index ->
//                if (index == 0) getValue(false).sorted()
//                else getValue(true).sorted()
//            }.reduce { a, b -> a+b }
//        }

//        return UiTrailTimeTable(bodyObject , parsingLocalTime?.first().toString() , parsingLocalTime?.last().toString())
        return UiTrailTimeTable(bodyObject , "" , "")
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