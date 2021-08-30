package com.young.cache.factory

import com.young.cache.factory.DataFactory.randomDouble
import com.young.cache.factory.DataFactory.randomString
import com.young.cache.model.CacheAllStationCodes
import com.young.cache.model.CacheFullRouteInformation
import com.young.cache.model.CacheStationNameAndMapXY
import com.young.cache.model.CacheTrailCodeAndLineCode

object ModelFactory {
    fun generateAllStationCodes(
        code: String,
        lineNum: String,
        stationCode: String,
        stationName: String
    ) = CacheAllStationCodes(code, lineNum, stationCode, stationName)

    fun generateFullRouteInformation(
        stationCode: String,
        stationName: String,
        routeCd: String,
        routNm: String,
        railCode: String,
        lineCode: String
    ) = CacheFullRouteInformation(
        stinCd = stationCode,
        stinNm = stationName,
        stinConsOrdr = randomString(),
        mreaWideCd = randomString(),
        routCd = routeCd,
        routNm = routNm,
        cacheTrailCodeAndLineCode = generateTrailCodeAndLineCode(railCode, lineCode)
    )

    fun generateTrailCodeAndLineCode(railCode: String, lineCode: String) =
        CacheTrailCodeAndLineCode(
            railOprIsttCd = railCode,
            lnCd = lineCode
        )

    fun generateStationNameAndMapXY(
        stinCd : String ,
        railCode: String,
        lineCode: String,
        stationName: String
    ) =
        CacheStationNameAndMapXY(
            stinCd = stinCd,
            cacheTrailCodeAndLineCode = generateTrailCodeAndLineCode(railCode, lineCode),
            stationName = stationName ,
            mapX = randomDouble(),
            mapY = randomDouble(),
            localCosX = randomDouble(),
            localCosY = randomDouble(),
            localSinX = randomDouble(),
            localSinY = randomDouble(),
        )
}