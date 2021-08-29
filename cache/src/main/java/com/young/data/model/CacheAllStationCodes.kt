package com.young.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["FR_CODE"])
data class CacheAllStationCodes(
    val FR_CODE: String,
    val LINE_NUM: String,
    val STATION_CD: String,
    val STATION_NM: String
)