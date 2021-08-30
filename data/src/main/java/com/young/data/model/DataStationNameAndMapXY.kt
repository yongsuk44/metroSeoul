package com.young.data.model


data class DataStationNameAndMapXY(
    val stinCd: String,
    val trailCodeAndLineCode: DataTrailCodeAndLineCode ,
    val stationName : String ,
    val mapX : Double ,
    val mapY : Double
)