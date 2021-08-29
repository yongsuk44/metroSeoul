package com.young.data.model

data class DataFullRouteInformation(
    val body: List<DataFullRouteInformationBody>,
    val header: DataHeader
)

data class DataFullRouteInformationBody(
    val mreaWideCd: String?,
    val railOprIsttCd: String,
    val routCd: String,
    val routNm: String?,
    val lnCd: String,
    val stinCd: String,
    val stinConsOrdr: String,
    val stinNm: String
)
