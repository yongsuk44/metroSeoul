package com.young.cache.model

data class DataConvenienceInformation(
    val body: List<DataConvenienceInformationBody>,
    val header: DataHeader
)

data class DataConvenienceInformationBody(
    val dtlLoc: String,
    val grndDvCd: String,
    val gubun: String,
    val imgPath: String,
    val mlFmlDvCd: String?,
    val stinFlor: Int,
    val trfcWeakDvCd: String?
)