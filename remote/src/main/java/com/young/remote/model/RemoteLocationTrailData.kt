package com.young.remote.model

data class RemoteLocationTrailData(
    val body: List<RemoteLocationData>,
    val header: Header
)

data class RemoteLocationData(
    val lnCd: String,
    val lonmAdr: String?,
    val mapCordX: Double?,
    val mapCordY: Double?,
    val railOprIsttCd: String,
    val roadNmAdr: String?,
    val stinCd: String,
    val stinLocLat: String?,
    val stinLocLon: String?,
    val stinNm: String,
    val stinNmEng: String,
    val stinNmJpn: String,
    val stinNmRom: String?,
    val stinNmSimpcina: String,
    val stinNmTradcina: String?,
    val strkZone: String?
)