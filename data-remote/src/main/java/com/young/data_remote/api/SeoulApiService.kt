package com.young.data_remote.api

import com.young.data_remote.model.RemoteSubWayTelPage
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SeoulApiService {
    @GET("{key}/json/subwayTourInfo/1/1000")
    suspend fun getSubWayTelData(
        @Path("key" , encoded = true) key : String
    ) : RemoteSubWayTelPage
}