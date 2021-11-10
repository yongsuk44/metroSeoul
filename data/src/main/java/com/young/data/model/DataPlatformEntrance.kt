package com.young.data.model

data class DataStationEntrance(
    val body: List<DataStationEntranceBody>?,
    val header: DataHeader
)

data class DataStationEntranceBody(
    val edMovePath: String,
    val elvtSttCd: String?,
    val elvtTpCd: String?,
    val exitMvTpOrdr: Int,
    val imgPath: String,
    val mvContDtl: String,
    val mvPathMgNo: Int,
    val stMovePath: String
)