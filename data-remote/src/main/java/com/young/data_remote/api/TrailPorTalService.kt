package com.young.data_remote.api

import com.young.data_remote.model.*
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

    @GET("trainUseInfo/subwayRouteInfo")
    suspend fun getFullRouteInformation(
        @Query("serviceKey", encoded = true) key: String,
        @Query("format") format: String,
        @Query("mreaWideCd") mreaWideCd: String
    ) : RemoteAllRouteInformation

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

    @GET("vulnerableUserInfo/stationMovement")
    suspend fun getPlatformAtTheEntranceData(
        @Query("serviceKey", encoded = true) key: String,
        @Query("format") format: String,
        @Query("railOprIsttCd") trailCode: String,
        @Query("lnCd") lineCode: String,
        @Query("stinCd") stinCd: String
    ) : RemotePlatformEntrance
}