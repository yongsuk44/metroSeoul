package com.young.data_remote.api

import com.young.data_remote.model.RemoteCustomerService
import com.young.data_remote.model.RemoteSubwayFacilitiesPage
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.Flow

interface PublicDataPortalApiService {

    @GET("15044443/v1/uddi:1bf6dd0f-ce92-40a2-840f-a9f5eeb60431")
    suspend fun getSubWayFacilitiesData(
        @Query("page") page : Int ,
        @Query("perPage") perPage : Int ,
        @Query(value = "serviceKey" , encoded = true) key : String
    ) : RemoteSubwayFacilitiesPage

    @GET("{apiService}/v1/uddi:{uddi}")
    suspend fun getStationLineTelData(
        @Path("apiService") service : String,
        @Path("uddi") code : String,
        @Query("page") page : Int ,
        @Query("perPage") perPage : Int ,
        @Query(value = "serviceKey" , encoded = true) key : String
    ) : RemoteCustomerService
}