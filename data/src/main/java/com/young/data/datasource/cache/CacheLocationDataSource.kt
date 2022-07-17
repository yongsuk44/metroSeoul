package com.young.data.datasource.cache

import com.young.data.model.DataUserLocationData
import kotlinx.coroutines.flow.Flow

interface CacheLocationDataSource {
    suspend fun readLastLocation() : Flow<DataUserLocationData>
    suspend fun updateLastLocation(userLocationData: DataUserLocationData) : Flow<DataUserLocationData>
}