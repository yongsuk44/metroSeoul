package com.young.data_remote.model

data class RemotePlatformEntrance(
    val body: List<PlatformEntranceBody>?,
    val header: Header
)

data class PlatformEntranceBody(
    val edMovePath: String,
    val elvtSttCd: String?,
    val elvtTpCd: String?,
    val exitMvTpOrdr: Int,
    val imgPath: String,
    val mvContDtl: String,
    val mvPathMgNo: Int,
    val stMovePath: String
)