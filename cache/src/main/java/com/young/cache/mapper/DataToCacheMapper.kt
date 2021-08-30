package com.young.cache.mapper

import com.young.base.BaseMapper
import com.young.cache.cache.model.*
import com.young.cache.model.CacheAllStationCodes
import com.young.cache.model.CacheFullRouteInformation
import com.young.cache.model.CacheStationNameAndMapXY
import com.young.cache.model.CacheTrailCodeAndLineCode
import kotlin.math.cos
import kotlin.math.sin

object DataToCacheMapper {
    fun DataTrailCodeAndLineCode.DataToCache() : CacheTrailCodeAndLineCode =
        BaseMapper<DataTrailCodeAndLineCode , CacheTrailCodeAndLineCode>().run {
            this(this@DataToCache)
        }

    fun DataRow.DataToCahe() : CacheAllStationCodes = BaseMapper<DataRow , CacheAllStationCodes>().run { this(this@DataToCahe) }

    fun DataAllStationCodes.DataToCache(): List<CacheAllStationCodes> {
        val row = SearchInfoBySubwayNameService.row
        return BaseMapper.setList(BaseMapper<DataRow , CacheAllStationCodes>()).run {
            this(row)
        }
    }

    fun DataFullRouteInformationBody.DataToCache() = with(::CacheFullRouteInformation) {
        CacheFullRouteInformation(
            stinCd = stinCd,
            mreaWideCd = mreaWideCd,
            cacheTrailCodeAndLineCode = CacheTrailCodeAndLineCode(railOprIsttCd, lnCd),
            routCd = routCd,
            routNm = routNm,
            stinConsOrdr = stinConsOrdr,
            stinNm = if (stinNm.contains("복정")) "복정" else stinNm
        )
    }

    fun DataStationNameAndMapXY.DataToCache() : CacheStationNameAndMapXY {
        val localSinX = sin(Math.toRadians(mapX))
        val localSinY = sin(Math.toRadians(mapY))
        val localCosX = cos(Math.toRadians(mapX))
        val localCosY = cos(Math.toRadians(mapY))

        return CacheStationNameAndMapXY(
            stinCd = stinCd,
            stationName = stationName,
            mapX = mapX,
            mapY = mapY,
            cacheTrailCodeAndLineCode = CacheTrailCodeAndLineCode(trailCodeAndLineCode.railOprIsttCd,trailCodeAndLineCode.lnCd) ,
            localSinX = localSinX ,
            localSinY = localSinY,
            localCosX = localCosX ,
            localCosY = localCosY
        )
    }
}