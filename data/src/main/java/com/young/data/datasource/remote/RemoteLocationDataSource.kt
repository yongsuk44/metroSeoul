package com.young.data.datasource.remote

import com.young.data.model.DataLocationTrailData
import kotlinx.coroutines.flow.Flow

interface RemoteLocationDataSource {
    suspend fun getStationAddress(
        key: String,
        trailCode: String,
        lineCode: String
    ): Flow<DataLocationTrailData>
}