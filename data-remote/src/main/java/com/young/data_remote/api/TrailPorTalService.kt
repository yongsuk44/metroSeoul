package com.young.data_remote.api

import com.young.data_remote.model.RemoteAllRouteInformation
import com.young.data_remote.model.RemoteTrailTimeTable
import retrofit2.http.GET
import retrofit2.http.Query

interface TrailPorTalService {

    @GET("trainUseInfo/subwayTimetable")
    suspend fun getTrailTimetables(
        @Query("serviceKey", encoded = true) key: String,
        @Query("format") format: String,
        @Query("railOprIsttCd") trailCode: String,
        @Query("dayCd") toDayCode: String,
        @Query("lnCd") lineCode: String,
        @Query("stinCd") stationCode: String
    ): RemoteTrailTimeTable

    @GET("trainUseInfo/subwayRouteInfo")
    suspend fun getFullRouteInformation(
        @Query("serviceKey", encoded = true) key: String,
        @Query("format") format: String,
        @Query("mreaWideCd") mreaWideCd: String,
        @Query("lnCd") lineCode: String
    ) : RemoteAllRouteInformation
}