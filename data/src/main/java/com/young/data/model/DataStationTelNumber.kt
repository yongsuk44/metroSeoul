package com.young.data.model

data class DataStationTelNumber(
    val response: DataResponse
)

data class DataResponse(
    val body: DataBody?,
    val header: DataTelHeader
)

data class DataTelHeader(
    val resultCode: String,
    val resultMsg: String,
    val type: String?
)
data class DataBody(
    val items: List<DataStationBody>?,
    val numOfRows: String,
    val pageNo: String,
    val totalCount: String
)

data class DataStationBody(
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