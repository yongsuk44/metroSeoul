package com.young.data.mapper

import com.young.data.model.FullRouteInformation
import com.young.data.model.LocalStationNameAndMapXY
import com.young.data.model.LocalTrailCodeAndLineCode
import com.young.domain.model.AllRouteInformation
import com.young.domain.model.DomainStationNameAndMapXY
import com.young.domain.model.DomainTrailCodeAndLineCode
import kotlin.math.cos
import kotlin.math.sin

object LocalToDomainMapper {


    fun AllRouteInformation.DomainToLocal() = with(::FullRouteInformation) {
        FullRouteInformation(
            stinCd = stinCd,
            mreaWideCd = mreaWideCd,
            localTrailCodeAndLineCode = LocalTrailCodeAndLineCode(railOprIsttCd, lnCd),
            routCd = routCd,
            routNm = routNm,
            stinConsOrdr = stinConsOrdr,
            stinNm = stinNm
        )
    }

    fun FullRouteInformation.LocalToDomain() = with(::AllRouteInformation) {
        AllRouteInformation(
            stinCd = stinCd,
            mreaWideCd = mreaWideCd,
            routCd = routCd,
            routNm = routNm,
            stinConsOrdr = stinConsOrdr,
            stinNm = stinNm,
            lnCd = localTrailCodeAndLineCode.lnCd ,
            railOprIsttCd = localTrailCodeAndLineCode.railOprIsttCd
        )
    }

    fun DomainStationNameAndMapXY.DomainToLocal() : LocalStationNameAndMapXY {
        val localSinX = sin(Math.toRadians(mapX))
        val localSinY = sin(Math.toRadians(mapY))
        val localCosX = cos(Math.toRadians(mapX))
        val localCosY = cos(Math.toRadians(mapY))

        return LocalStationNameAndMapXY(
            stinCd = stinCd,
            stationName = stationName,
            mapX = mapX,
            mapY = mapY,
            localTrailCodeAndLineCode = LocalTrailCodeAndLineCode(trailCodeAndLineCode.railOprIsttCd,trailCodeAndLineCode.lnCd) ,
            localSinX = localSinX ,
            localSinY = localSinY,
            localCosX = localCosX ,
            localCosY = localCosY
        )
    }

    fun LocalStationNameAndMapXY.LocalToDomain() : DomainStationNameAndMapXY {
        return DomainStationNameAndMapXY(
            stinCd = stinCd,
            stationName = stationName,
            mapX = mapX,
            mapY = mapY,
            trailCodeAndLineCode = DomainTrailCodeAndLineCode(localTrailCodeAndLineCode.railOprIsttCd,localTrailCodeAndLineCode.lnCd)
        )
    }
}