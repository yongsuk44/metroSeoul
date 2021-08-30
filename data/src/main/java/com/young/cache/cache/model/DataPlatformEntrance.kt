package com.young.cache.cache.model

data class DataPlatformEntrance(
    val body: List<DataPlatformEntranceBody>?,
    val header: DataHeader
)

data class DataPlatformEntranceBody(
    val edMovePath: String,
    val elvtSttCd: String?,
    val elvtTpCd: String?,
    val exitMvTpOrdr: Int,
    val imgPath: String,
    val mvContDtl: String,
    val mvPathMgNo: Int,
    val stMovePath: String
)