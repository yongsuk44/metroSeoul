package com.young.remote.model

data class RemoteAllStationCodes(
    val SearchInfoBySubwayNameService: RemoteSearchInfoBySubwayNameService
)

data class RemoteSearchInfoBySubwayNameService(
    val RESULT: RemoteRESULT,
    val list_total_count: Int,
    val row: List<RemoteRow>
)

data class RemoteRESULT(
    val CODE: String,
    val MESSAGE: String
)

data class RemoteRow(
    val FR_CODE: String,
    val LINE_NUM: String,
    val STATION_CD: String,
    val STATION_NM: String
)