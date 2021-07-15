package com.young.domain.repository.subwayfacilities

import com.young.domain.model.DomainSubwayFacilities
import kotlinx.coroutines.flow.Flow
import java.util.*

interface LocalSubWayFacilitiesRepository {

    suspend fun insertSubWayFacilitiesDataAll(items: List<DomainSubwayFacilities>)

    suspend fun updateSubWayFacilitiesData(items : List<DomainSubwayFacilities>)

    suspend fun getLineMetroData(lineNumber: String): Flow<List<DomainSubwayFacilities>>

    suspend fun getStationData(stationNumber : String) : Flow<DomainSubwayFacilities>

}