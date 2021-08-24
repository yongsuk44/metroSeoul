package com.young.domain.repository.subwayfacilities

import com.young.domain.model.DomainSubwayFacilities
import kotlinx.coroutines.flow.Flow

interface CacheSubWayFacilitiesRepository {

    suspend fun insertSubWayFacilitiesDataAll(items: List<DomainSubwayFacilities>)

    suspend fun updateSubWayFacilitiesData(items : List<DomainSubwayFacilities>)

    suspend fun getLineMetroData(lineNumber: String): Flow<List<DomainSubwayFacilities>>

    suspend fun getStationData(stationNumber : String) : Flow<DomainSubwayFacilities>

    suspend fun getDataSize() : Int

    suspend fun getCacheAllData() : Flow<List<DomainSubwayFacilities>>
}