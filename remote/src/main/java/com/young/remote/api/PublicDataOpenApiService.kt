package com.young.remote.api

import com.young.remote.model.RemoteStationTelNumber
import retrofit2.http.GET
import retrofit2.http.Query

interface PublicDataOpenApiService {
    @GET("tn_pubr_public_ctyrlroad_statn_api")
    suspend fun getAllRouteInformationData(
        @Query("serviceKey" , encoded = true) key : String ,
        @Query("pageNo") page : Int ,
        @Query("numOfRows") row : Int ,
        @Query("type") type : String ,
        @Query("statnNo") stationCode : String
    ) : RemoteStationTelNumber
}