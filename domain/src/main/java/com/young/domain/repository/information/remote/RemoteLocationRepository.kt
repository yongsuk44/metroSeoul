package com.young.domain.repository.information.remote

import com.young.domain.model.LocationData
import kotlinx.coroutines.flow.Flow

interface RemoteLocationRepository {
    suspend fun getStationAddress(key : String , trailCode : String , lineCode : String) : Flow<List<LocationData>>
}