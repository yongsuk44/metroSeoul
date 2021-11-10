package com.young.remote.model

data class RemoteStationEntrance(
    val body: List<StationEntranceBody>?,
    val header: Header
)

data class StationEntranceBody(
    val edMovePath: String,
    val elvtSttCd: String?,
    val elvtTpCd: String?,
    val exitMvTpOrdr: Int,
    val imgPath: String,
    val mvContDtl: String,
    val mvPathMgNo: Int,
    val stMovePath: String
)