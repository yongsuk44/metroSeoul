package com.young.cache.mapper

import com.young.cache.model.CacheFullRouteInformation
import com.young.cache.model.CacheAllStationCodes
import com.young.cache.model.CacheStationNameAndMapXY
import com.young.cache.model.CacheTrailCodeAndLineCode

object CacheToDataMapper {
    fun DataRow.DataToLocal() = with(::CacheAllStationCodes) {
        CacheAllStationCodes(
            FR_CODE = FR_CODE,
            LINE_NUM = LINE_NUM,
            STATION_CD = STATION_CD,
            STATION_NM = STATION_NM
        )
    }

    fun AllRouteInformation.DataToCache() = with(::CacheFullRouteInformation) {
        CacheFullRouteInformation(
            stinCd = stinCd,
            mreaWideCd = mreaWideCd,
            localTrailCodeAndLineCode = LocalTrailCodeAndLineCode(railOprIsttCd, lnCd),
            routCd = routCd,
            routNm = routNm,
            stinConsOrdr = stinConsOrdr,
            stinNm = if (stinNm.contains("복정")) "복정" else stinNm
        )
    }

    fun CacheFullRouteInformation.CacheToData() = with(::AllRouteInformation) {
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

    fun CacheStationNameAndMapXY.DataToLocal() : DataStationNameAndMapXY {
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

    fun CacheStationNameAndMapXY.LocalToData() : DataStationNameAndMapXY {
        return DataStationNameAndMapXY(
            stinCd = stinCd,
            stationName = stationName,
            mapX = mapX,
            mapY = mapY,
            trailCodeAndLineCode = DataTrailCodeAndLineCode(localTrailCodeAndLineCode.railOprIsttCd,localTrailCodeAndLineCode.lnCd)
        )
    }
}