package com.young.domain.model

data class DomainStationTelNumber(
    val response: Response
)

data class Response(
    val body: Body?,
    val header: TelHeader
)

data class TelHeader(
    val resultCode: String,
    val resultMsg: String,
    val type: String?
)
data class Body(
    val items: List<DomainStationBody>,
    val numOfRows: String,
    val pageNo: String,
    val totalCount: String
)

data class DomainStationBody(
    val institutionNm: String,
    val insttCode: String,
    val latitude: String,
    val longitude: String,
    val phoneNumber: String,
    val rdnmadr: String,
    val referenceDate: String,
    val routeNm: String,
    val routeNo: String,
    val statnChcrt: String,
    val statnEnm: String,
    val statnNm: String,
    val statnNo: String,
    val trnsitlcRouteNm: String,
    val trnsitlcRouteNo: String,
    val trnsitlcSe: String
)