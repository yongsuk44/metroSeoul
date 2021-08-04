package com.young.presentation.modelfunction

interface DetailStationInformationViewFunction {

    fun getStationData(stinCodes : List<String>)

    fun getSubWayTelData(key: String)

    fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    )

    fun getTrailTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String
    )
}