package com.young.domain.model

data class DomainConvenienceInformation(
    val body: List<DomainConvenienceInformationBody>,
    val header: Header
)

data class DomainConvenienceInformationBody(
    val dtlLoc: String,
    val grndDvCd: String,
    val gubun: String,
    val imgPath: String,
    val mlFmlDvCd: String?,
    val stinFlor: Int,
    val trfcWeakDvCd: String?
)