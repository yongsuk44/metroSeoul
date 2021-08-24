package com.young.domain.model

data class DomainAllStationCodes(
    val SearchInfoBySubwayNameService: DomainSearchInfoBySubwayNameService
)

data class DomainSearchInfoBySubwayNameService(
    val RESULT: DomainRESULT,
    val list_total_count: Int,
    val row: List<DomainRow>
)

data class DomainRESULT(
    val CODE: String,
    val MESSAGE: String
)

data class DomainRow(
    val FR_CODE: String,
    val LINE_NUM: String,
    val STATION_CD: String,
    val STATION_NM: String
)