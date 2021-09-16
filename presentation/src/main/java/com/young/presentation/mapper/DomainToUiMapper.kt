package com.young.presentation.mapper

import android.location.Location
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.*
import com.young.presentation.model.*
import kotlinx.coroutines.flow.*

object DomainToUiMapper {

    fun Row.DomainToUi(): Row {
        return BaseMapper<Row, Row>().run { this(this@DomainToUi) }
    }

    fun DomainFullRouteInformationBody.DomainToUi(): IndexAllRouteInformation {

        val bodyMapper = BaseMapper(
            DomainFullRouteInformationBody::class,
            IndexAllRouteInformation::class
        )

        bodyMapper.run {
            return this(this@DomainToUi)
        }
    }

    fun List<DomainFullRouteInformationBody>.DomainToUi(): List<ListRouteInformation> {
        return this.groupBy {
            it.stinNm
        }.map {
            it.value.run {
                ListRouteInformation(
                    first().mreaWideCd,
                    map { it.railOprIsttCd },
                    first().routCd,
                    first().routNm,
                    map { it.lnCd },
                    map { it.stinCd },
                    first().stinConsOrdr,
                    first().stinNm,
                )
            }
        }
    }

    fun DomainConvenienceInformation.DomainToUi(): UiConvenienceInformation {
        val convenienceMapper = BaseMapper(
            DomainConvenienceInformation::class,
            UiConvenienceInformation::class
        )
        val headerMapper = BaseMapper(
            com.young.domain.model.Header::class,
            com.young.presentation.model.Header::class
        )
        val bodyMapper = BaseMapper(
            ConvenienceInformationBody::class,
            ConvenienceInformationBody::class
        )

        convenienceMapper.apply {
            register("header", headerMapper)
            register("body", BaseMapper.setList(bodyMapper))
        }.run {
            return this(this@DomainToUi)
        }
    }

    fun UiStationNameAndMapXY.UiToDomain(): DomainStationNameAndMapXY {
        return DomainStationNameAndMapXY(
            stinCd = stinCd,
            stationName = stationName,
            mapX = mapX,
            mapY = mapY,
            trailCodeAndLineCode = DomainTrailCodeAndLineCode(
                trailCodeAndLineCode.railOprIsttCd,
                trailCodeAndLineCode.lnCd
            )
        )
    }

    fun DomainStationNameAndMapXY.DomainToUi(): UiStationNameAndMapXY {
        return UiStationNameAndMapXY(
            stinCd = stinCd,
            stationName = stationName,
            mapX = mapX,
            mapY = mapY,
            trailCodeAndLineCode = UiTrailCodeAndLineCode(
                trailCodeAndLineCode.railOprIsttCd,
                trailCodeAndLineCode.lnCd
            )
        )
    }

    fun List<DomainStationNameAndMapXY>.DomainToUiDistance(
        nowLocationLatitude: Double,
        nowLocationLongitude: Double
    ): List<UiStationNameDistance> {
        return groupBy {
            it.mapX to it.mapY
        }.map {
            it.value.run {
                UiStationNameDistance(
                    map { it.stinCd },
                    map { it.trailCodeAndLineCode.railOprIsttCd },
                    map { it.trailCodeAndLineCode.lnCd },
                    first().stationName,
                    getStartPosEndPosDistanceData(
                        nowLocationLatitude,
                        nowLocationLongitude,
                        first().mapX,
                        first().mapY
                    )
                )
            }
        }
    }

    fun getStartPosEndPosDistanceData(
        nowLocationLatitude: Double,
        nowLocationLongitude: Double,
        x: Double,
        y: Double
    ): Int {
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

    fun DomainPlatformEntrance.DomainToUi(): UiPlatformEntrance {
        val mapper = BaseMapper<DomainPlatformEntrance, UiPlatformEntrance>()
        val body =
            BaseMapper<PlatformEntranceBody, com.young.presentation.model.PlatformEntranceBody>()
        val header = BaseMapper(
            com.young.domain.model.Header::class,
            com.young.presentation.model.Header::class
        )

        mapper.apply {
            register("header", header)
            register("body", BaseMapper.setList(body))
        }.run {
            return this(this@DomainToUi)
        }
    }
}