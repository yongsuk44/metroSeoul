package com.young.domain.repository.subwayfacilities

import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.model.DomainSubwayFacilitiesPage
import kotlinx.coroutines.flow.Flow

interface RemoteSubWayFacilitiesRepository {
    suspend fun getSubWayFacilitiesData(key: String): Flow<List<DomainSubwayFacilities>>
}