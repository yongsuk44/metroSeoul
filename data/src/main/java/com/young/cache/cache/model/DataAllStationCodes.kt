package com.young.cache.cache.model

data class DataAllStationCodes(
    val SearchInfoBySubwayNameService: DataSearchInfoBySubwayNameService
)

data class DataSearchInfoBySubwayNameService(
    val RESULT: DataRESULT,
    val list_total_count: Int,
    val row: List<DataRow>
)

data class DataRESULT(
    val CODE: String,
    val MESSAGE: String
)

data class DataRow(
    val FR_CODE: String,
    val LINE_NUM: String,
    val STATION_CD: String,
    val STATION_NM: String
)