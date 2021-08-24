package com.young.remote.repository

import com.young.cache.datasource.remote.RemoteStationDataSource
import com.young.cache.model.DataStationSeoulTimeTable
import com.young.cache.model.DataStationTelNumber
import com.young.cache.model.DataStationTimeTable
import com.young.remote.api.PublicDataOpenApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteStationDataRepositoryImpl @Inject constructor(
    private val publicDataPortalApiService: PublicDataOpenApiService
) : RemoteStationDataSource {
    override suspend fun getStationTelData(
        publicDataKey: String,
        stationCode: String
    ): Flow<DataStationTelNumber> {
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