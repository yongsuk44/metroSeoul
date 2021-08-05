package com.young.presentation.mapper

import android.location.Geocoder
import android.location.Location
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.*
import com.young.domain.model.AllRouteInformation
import com.young.domain.model.ConvenienceInformationBody
import com.young.domain.model.Header
import com.young.domain.model.TimeTableBody
import com.young.presentation.model.*
import timber.log.Timber

object DomainToUiMapper {
    fun DomainTrailTimeTable.DomaionToUi(): UiTrailTimeTable {
        val timeTableMapper = BaseMapper(DomainTrailTimeTable::class, UiTrailTimeTable::class)
        val headerMapper = BaseMapper(Header::class, com.young.presentation.model.Header::class)
        val timeTableBody =
            BaseMapper(TimeTableBody::class, com.young.presentation.model.TimeTableBody::class)

        timeTableMapper.apply {
            register("header", headerMapper)
            register("body", BaseMapper.setList(timeTableBody))
        }.run {
            return this(this@DomaionToUi)
        }
    }

    fun DomainAllRouteInformation.DomainToUi(): UiAllRouteInformation {
        val timeTableMapper =
            BaseMapper(DomainAllRouteInformation::class, UiAllRouteInformation::class)
        val headerMapper =
            BaseMapper(Header::class, com.young.presentation.model.Header::class)
        val bodyMapper = BaseMapper(
            AllRouteInformation::class,
            com.young.presentation.model.AllRouteInformation::class
        )

        timeTableMapper.apply {
            register("header", headerMapper)
            register("body", BaseMapper.setList(bodyMapper))
        }.run {
            return this(this@DomainToUi)
        }
    }

    fun List<AllRouteInformation>.DomainToUi(): List<com.young.presentation.model.AllRouteInformation> {
        return this.groupBy {
            it.stinNm
        }.map {
            it.value.run {

                if (this.size >= 2) {
                    com.young.presentation.model.AllRouteInformation(
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
                    com.young.presentation.model.AllRouteInformation(
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
}