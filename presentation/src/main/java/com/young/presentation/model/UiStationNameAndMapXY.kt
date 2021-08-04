package com.young.presentation.model


data class UiStationNameAndMapXY(
    val stinCd: String,
    val trailCodeAndLineCode: UiTrailCodeAndLineCode ,
    val stationName : String ,
    val mapX : Double ,
    val mapY : Double
)

data class UiStationNameDistance(
    val stinCd: List<String>,
    val trailCode: List<String>,
    val lineCode: List<String>,
    val stationName : String ,
    val distance : Int
)