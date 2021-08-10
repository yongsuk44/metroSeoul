package com.young.data.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalStationTel(
    @PrimaryKey val stinCd : String,
    val stationName : String ,
    val telNumber : String
)