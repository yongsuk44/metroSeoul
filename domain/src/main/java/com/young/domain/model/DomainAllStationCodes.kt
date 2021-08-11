package com.young.domain.model

data class DomainAllStationCodes(
    val SearchInfoBySubwayNameService: SearchInfoBySubwayNameService
)

data class SearchInfoBySubwayNameService(
    val RESULT: RESULT,
    val list_total_count: Int,
    val row: List<Row>
)

data class RESULT(
    val CODE: String,
    val MESSAGE: String
)

data class Row(
    val FR_CODE: String,
    val LINE_NUM: String,
    val STATION_CD: String,
    val STATION_NM: String
)