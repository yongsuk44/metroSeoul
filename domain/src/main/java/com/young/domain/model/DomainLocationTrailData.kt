package com.young.domain.model

data class DomainLocationTrailData(
    val body: List<LocationData>,
    val header: Header
)

data class LocationData(
    val lnCd: String,
    val lonmAdr: String,
    val mapCordX: Double?,
    val mapCordY: Double?,
    val railOprIsttCd: String,
    val roadNmAdr: String,
    val stinCd: String,
    val stinLocLat: String?,
    val stinLocLon: String?,
    val stinNm: String,
    val stinNmEng: String,
    val stinNmJpn: String,
    val stinNmRom: String,
    val stinNmSimpcina: String,
    val stinNmTradcina: String,
    val strkZone: String?
)