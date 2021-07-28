package com.young.presentation.model


data class UiStationNameAndMapXY(
    val stinCd: String,
    val trailCodeAndLineCode: UiTrailCodeAndLineCode ,
    val stationName : String ,
    val mapX : Double ,
    val mapY : Double
)