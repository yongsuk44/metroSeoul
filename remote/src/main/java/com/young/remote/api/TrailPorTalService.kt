package com.young.remote.api

import com.young.remote.model.*
import retrofit2.http.GET
import retrofit2.http.Query

interface TrailPorTalService {

    @GET("convenientInfo/stationTimetable")
    suspend fun getStationTimetables(
        @Query("serviceKey", encoded = true) key: String,
        @Query("format") format: String,
        @Query("railOprIsttCd") trailCode: String,
        @Query("dayCd") toDayCode: String,
        @Query("lnCd") lineCode: String,
        @Query("stinCd") stationCode: String
    ): RemoteStationTimeTable

    // 지하철 라인별 노선
    @GET("trainUseInfo/subwayRouteInfo")
    suspend fun getStationRouteInformation(
        @Query("serviceKey", encoded = true) key: String,
        @Query("format") format: String,
        @Query("mreaWideCd") mreaWideCd: String ,
        @Query("lnCd") lineCode: String?
    ) : RemoteFullRouteInformation

    // 편의 정보
    @GET("convenientInfo/stationCnvFacl")
    suspend fun getConvenienceInformation(
        @Query("serviceKey", encoded = true) key: String,
        @Query("format") format: String,
        @Query("lnCd") lineCode: String ,
        @Query("railOprIsttCd") trailCode: String,
        @Query("stinCd") stationCode: String
    ) : RemoteConvenienceInformation

    @GET("convenientInfo/stationInfo")
    suspend fun getStationAddressData(
        @Query("serviceKey", encoded = true) key: String,
        @Query("format") format: String,
        @Query("railOprIsttCd") trailCode: String,
        @Query("lnCd") lineCode: String
    ) : RemoteLocationTrailData

    // 출입구에서 승강장까지 출입 경로
    @GET("vulnerableUserInfo/stationMovement")
    suspend fun getPlatformEntranceData(
        @Query("serviceKey", encoded = true) key: String,
        @Query("format") format: String,
        @Query("railOprIsttCd") trailCode: String,
        @Query("lnCd") lineCode: String,
        @Query("stinCd") stinCd: String
    ) : RemotePlatformEntrance
}