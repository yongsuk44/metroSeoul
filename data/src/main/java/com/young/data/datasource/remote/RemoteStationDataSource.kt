package com.young.data.datasource.remote

import com.young.data.model.DataStationBody
import com.young.data.model.DataStationSeoulTimeTable
import com.young.data.model.DataStationTelNumber
import com.young.data.model.DataStationTimeTable
import kotlinx.coroutines.flow.Flow

interface RemoteStationDataSource {
    suspend fun getStationTelData(publicDataKey: String , stationCode: String) : Flow<List<DataStationBody>>
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