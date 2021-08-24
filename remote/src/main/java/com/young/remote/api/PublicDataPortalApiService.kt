package com.young.remote.api

import com.young.remote.model.RemoteSubwayFacilitiesPage
import retrofit2.http.GET
import retrofit2.http.Query

interface PublicDataPortalApiService {

    @GET("15044443/v1/uddi:1bf6dd0f-ce92-40a2-840f-a9f5eeb60431")
    suspend fun getSubWayFacilitiesData(
        @Query("page") page : Int ,
        @Query("perPage") perPage : Int ,
        @Query(value = "serviceKey" , encoded = true) key : String
    ) : RemoteSubwayFacilitiesPage
}