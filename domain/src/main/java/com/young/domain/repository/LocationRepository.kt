package com.young.domain.repository

import com.young.domain.model.DomainLocationTrailData
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getStationAddress(key : String , railCdoe : String , lineCode : String) : Flow<DomainLocationTrailData>

}