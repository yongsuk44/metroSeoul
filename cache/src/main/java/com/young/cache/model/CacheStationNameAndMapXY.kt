package com.young.cache.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CacheStationNameAndMapXY(
    @PrimaryKey val stinCd: String,
    @Embedded val cacheTrailCodeAndLineCode: CacheTrailCodeAndLineCode,
    val stationName : String ,
    val mapX : Double ,
    val mapY : Double ,
    val localCosX : Double ,
    val localCosY : Double ,
    val localSinX : Double ,
    val localSinY : Double
)