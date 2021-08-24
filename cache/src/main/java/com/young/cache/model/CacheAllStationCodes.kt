package com.young.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["FR_CODE"])
data class CacheAllStationCodes(
    val FR_CODE: String,
    val LINE_NUM: String,
    val STATION_CD: String,
    val STATION_NM: String
)