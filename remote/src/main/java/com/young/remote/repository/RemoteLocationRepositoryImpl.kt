package com.young.remote.repository

import com.young.cache.cache.datasource.remote.RemoteLocationDataSource
import com.young.cache.cache.model.DataLocationTrailData
import com.young.remote.api.TrailPorTalService
import com.young.remote.mapper.RemoteToDataMapper.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RemoteLocationRepositoryImpl @Inject constructor(
    private val service: TrailPorTalService
) : RemoteLocationDataSource {
    override suspend fun getStationAddress(
        key: String,
        trailCode: String,
        lineCode: String
    ): Flow<DataLocationTrailData> = flowOf(
        service.getStationAddressData(key, "json", trailCode, lineCode).RemoteData()
    )
}