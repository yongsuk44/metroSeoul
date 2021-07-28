package com.young.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalStationNameAndMapXY(
    @PrimaryKey val stinCd: String,
    @Embedded val localTrailCodeAndLineCode: LocalTrailCodeAndLineCode,
    val stationName : String ,
    val mapX : Double ,
    val mapY : Double
)