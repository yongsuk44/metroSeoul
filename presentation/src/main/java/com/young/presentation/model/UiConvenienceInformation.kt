package com.young.presentation.model

data class UiConvenienceInformation(
    val body: List<ConvenienceInformationBody>,
    val header: Header
)

data class ConvenienceInformationBody(
    val dtlLoc: String,
    val grndDvCd: String,
    val gubun: String,
    val imgPath: String,
    val mlFmlDvCd: String?,
    val stinFlor: Int,
    val trfcWeakDvCd: String?
)