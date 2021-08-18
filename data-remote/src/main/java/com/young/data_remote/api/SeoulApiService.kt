package com.young.data_remote.api

import com.young.data_remote.model.RemoteAllStationCodes
import com.young.data_remote.model.RemoteStationSeoulTimeTable
import retrofit2.http.GET
import retrofit2.http.Path

interface SeoulApiService {
    @GET("{key}/json/SearchInfoBySubwayNameService/1/1000")
    suspend fun getStationNameToAllStationCodes(
        @Path("key" , encoded = true) key : String
    ) : RemoteAllStationCodes

    @GET("{key}/json/SearchSTNTimeTableByIDService/1/500/{stationCode}/{day}/{updown}")
    suspend fun getStationTimeTable(
        @Path("key" , encoded = true) key : String ,
        @Path("stationCode") code : String ,
        @Path("day") dayCode : String ,
        @Path("updown") updown : String
    ) : RemoteStationSeoulTimeTable
}