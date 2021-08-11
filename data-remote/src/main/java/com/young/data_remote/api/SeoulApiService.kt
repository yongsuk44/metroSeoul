package com.young.data_remote.api

import com.young.data_remote.model.RemoteAllStationCodes
import retrofit2.http.GET
import retrofit2.http.Path

interface SeoulApiService {
    @GET("{key}/json/SearchInfoBySubwayNameService/1/1000")
    suspend fun getStationNameToAllStationCodes(
        @Path("key" , encoded = true) key : String
    ) : RemoteAllStationCodes
}