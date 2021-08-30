package com.young.cache.cache.datasource.remote

import com.young.cache.cache.model.DataLocationTrailData
import kotlinx.coroutines.flow.Flow

interface RemoteLocationDataSource {
    suspend fun getStationAddress(
        key: String,
        trailCode: String,
        lineCode: String
    ): Flow<DataLocationTrailData>
}