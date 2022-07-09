package com.young.domain.repository.subwayfacilities

import com.young.domain.model.DomainSubwayFacilities
import kotlinx.coroutines.flow.Flow

interface SubWayFacilitiesRepository {
    suspend fun getSubWayFacilitiesData(key: String): Flow<List<DomainSubwayFacilities>>
}