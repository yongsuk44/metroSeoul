package com.young.domain.repository

import com.young.domain.model.DomainSubwayFacilities
import kotlinx.coroutines.flow.Flow

interface SubWayFacilitiesRepository {

    suspend fun insertSubWayFacilitiesDataAll(items: List<DomainSubwayFacilities>)

    suspend fun updateSubWayFacilitiesData(items : List<DomainSubwayFacilities>)

    suspend fun readLineMetroData(lineNumber: String): Flow<List<DomainSubwayFacilities>>

    suspend fun readStationData(stationNumber : String) : Flow<DomainSubwayFacilities>

    suspend fun readDataSize() : Int

    suspend fun readCacheAllData() : Flow<List<DomainSubwayFacilities>>
}