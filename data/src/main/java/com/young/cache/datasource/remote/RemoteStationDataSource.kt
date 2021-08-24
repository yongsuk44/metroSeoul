package com.young.cache.datasource.remote

import com.young.cache.model.DataStationSeoulTimeTable
import com.young.cache.model.DataStationTelNumber
import com.young.cache.model.DataStationTimeTable
import kotlinx.coroutines.flow.Flow

interface RemoteStationDataSource {
    suspend fun getStationTelData(publicDataKey: String , stationCode: String) : Flow<DataStationTelNumber>
    suspend fun getDataStationTimeTable(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String ,
        updown: String
    ): Flow<DataStationTimeTable>

    suspend fun getDataSeoulStationTimeTable(
        key: String,
        updown : String,
        dayCd: String,
        stationCode: String
    ): Flow<DataStationSeoulTimeTable>
}