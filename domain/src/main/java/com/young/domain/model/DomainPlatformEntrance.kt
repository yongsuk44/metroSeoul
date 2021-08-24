package com.young.domain.model

data class DomainPlatformEntrance(
    val body: List<DomainPlatformEntranceBody>?,
    val header: Header
)

data class DomainPlatformEntranceBody(
    val edMovePath: String,
    val elvtSttCd: String?,
    val elvtTpCd: String?,
    val exitMvTpOrdr: Int,
    val imgPath: String,
    val mvContDtl: String,
    val mvPathMgNo: Int,
    val stMovePath: String
)