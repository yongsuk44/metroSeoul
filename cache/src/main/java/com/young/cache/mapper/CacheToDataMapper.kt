package com.young.cache.mapper

import com.young.base.BaseMapper
import com.young.cache.cache.model.*
import com.young.cache.model.CacheAllStationCodes
import com.young.cache.model.CacheFullRouteInformation
import com.young.cache.model.CacheStationNameAndMapXY
import com.young.cache.model.CacheTrailCodeAndLineCode

object CacheToDataMapper {
    fun CacheTrailCodeAndLineCode.CacheToData() : DataTrailCodeAndLineCode =
        BaseMapper<CacheTrailCodeAndLineCode, DataTrailCodeAndLineCode>().run {
            this(this@CacheToData)
        }

    fun CacheFullRouteInformation.CacheToData() = with(::DataFullRouteInformationBody) {
        DataFullRouteInformationBody(
            stinCd = stinCd,
            mreaWideCd = mreaWideCd,
            routCd = routCd,
            routNm = routNm,
            stinConsOrdr = stinConsOrdr,
            stinNm = stinNm,
            lnCd = cacheTrailCodeAndLineCode.lnCd ,
            railOprIsttCd = cacheTrailCodeAndLineCode.railOprIsttCd
        )
    }

    fun CacheStationNameAndMapXY.CacheToData() : DataStationNameAndMapXY {
        return DataStationNameAndMapXY(
            stinCd = stinCd,
            stationName = stationName,
            mapX = mapX,
            mapY = mapY,
            trailCodeAndLineCode = DataTrailCodeAndLineCode(cacheTrailCodeAndLineCode.railOprIsttCd,cacheTrailCodeAndLineCode.lnCd)
        )
    }

    fun CacheAllStationCodes?.CacheToData() : DataRow? {
        return if (this == null) null
        else {
            BaseMapper<CacheAllStationCodes, DataRow>().run {
                this(this@CacheToData)
            }
        }
    }
}