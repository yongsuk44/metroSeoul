package com.young.data_remote.datasource

import com.young.data_remote.model.RemoteStationSeoulTimeTable
import com.young.data_remote.model.RemoteStationTimeTable
import kotlinx.coroutines.flow.Flow

interface RemoteStationTimeDataSource {
    suspend fun getRemoteStationTimeTable(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String ,
        updown: String
    ): Flow<RemoteStationTimeTable>

    suspend fun getRemoteSeoulStationTimeTable(
        key: String,
        updown : String,
        dayCd: String,
        stationCode: String
    ): Flow<RemoteStationSeoulTimeTable>
}