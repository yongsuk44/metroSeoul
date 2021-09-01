package com.young.domain.repository.location

import com.young.domain.model.DomainStationNameAndMapXY
import kotlinx.coroutines.flow.Flow

interface CacheStationCoordinatesRepository {
    suspend fun insertStationCoordinateData(items : List<DomainStationNameAndMapXY>) : Flow<List<Long>>
    suspend fun getStationCoordinateAllData() : Flow<List<DomainStationNameAndMapXY>>
    suspend fun getStationCoordinateDataSize() : Flow<Int>

    suspend fun getLocationNearStationList(lastX : Double , lastY :Double , km : Double) : Flow<List<DomainStationNameAndMapXY>>
}