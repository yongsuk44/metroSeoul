package com.young.domain.model

data class DomainStationEntrance(
    val body: List<DomainStationEntranceBody>?,
    val header: Header
)

data class DomainStationEntranceBody(
    val edMovePath: String,
    val elvtSttCd: String?,
    val elvtTpCd: String?,
    val exitMvTpOrdr: Int,
    val imgPath: String,
    val mvContDtl: String,
    val mvPathMgNo: Int,
    val stMovePath: String
)