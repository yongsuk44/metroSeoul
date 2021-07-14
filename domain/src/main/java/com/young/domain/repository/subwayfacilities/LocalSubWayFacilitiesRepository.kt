package com.young.domain.repository.subwayfacilities

import com.young.domain.model.DomainSubwayFacilities
import java.util.*

interface LocalSubWayFacilitiesRepository {
    suspend fun getLineMetroData(
        lineNumber: String
    ): List<DomainSubwayFacilities>

    suspend fun insertSubWayFacilitiesDataAll(
        items: List<DomainSubwayFacilities>
    ): List<Long>

    suspend fun getStationData(
        stationNumber : String
    ) : DomainSubwayFacilities

    suspend fun updateSubWayFacilitiesData(
        items : List<DomainSubwayFacilities>
    ) : List<Long>
}