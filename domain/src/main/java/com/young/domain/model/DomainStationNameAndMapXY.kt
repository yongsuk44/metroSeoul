package com.young.domain.model


data class DomainStationNameAndMapXY(
    val stinCd: String,
    val trailCodeAndLineCode: DomainTrailCodeAndLineCode ,
    val stationName : String ,
    val mapX : Double ,
    val mapY : Double
)