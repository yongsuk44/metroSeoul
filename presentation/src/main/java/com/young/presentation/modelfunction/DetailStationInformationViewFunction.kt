package com.young.presentation.modelfunction

interface DetailStationInformationViewFunction {
    suspend fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    )

    fun loadTrailTimeTableData(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String
    )

    suspend fun getTrailTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String
    )
}