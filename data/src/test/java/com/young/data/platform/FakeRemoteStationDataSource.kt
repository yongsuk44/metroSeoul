package com.young.data.platform

import com.young.data.datasource.remote.RemoteStationDataSource
import com.young.data.model.DataStationBody
import com.young.data.model.DataStationSeoulTimeTable
import com.young.data.model.DataStationTimeTable
import kotlinx.coroutines.flow.Flow

interface FakeRemoteStationDataSource : RemoteStationDataSource {
    override suspend fun getStationTelData(
        publicDataKey: String,
        stationCode: String
    ): Flow<List<DataStationBody>?> {
        TODO("Not yet implemented")
    }

    override suspend fun getDataStationTimeTable(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String,
        updown: String
    ): Flow<DataStationTimeTable> {
        TODO("Not yet implemented")
    }

    override suspend fun getDataSeoulStationTimeTable(
        key: String,
        updown: String,
        dayCd: String,
        stationCode: String
    ): Flow<DataStationSeoulTimeTable> {
        TODO("Not yet implemented")
    }
}