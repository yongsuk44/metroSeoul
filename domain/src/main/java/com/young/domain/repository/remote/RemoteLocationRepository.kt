package com.young.domain.repository.remote

import com.young.domain.model.DomainLocationTrailData
import kotlinx.coroutines.flow.Flow

interface RemoteLocationRepository {
    suspend fun getStationAddress(key : String , railCdoe : String , lineCode : String) : Flow<DomainLocationTrailData>

}