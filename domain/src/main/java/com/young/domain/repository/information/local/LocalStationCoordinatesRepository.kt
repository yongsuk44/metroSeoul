package com.young.domain.repository.information.local

import com.young.domain.model.DomainStationNameAndMapXY
import kotlinx.coroutines.flow.Flow

interface LocalStationCoordinatesRepository {
    suspend fun insertStationCoordinateData(items : List<DomainStationNameAndMapXY>)
    suspend fun getStationCoordinateAllData() : Flow<List<DomainStationNameAndMapXY>>
    suspend fun getStationCoordinateDataSize() : Flow<Int>

    suspend fun getLocationNearStationList(lastX : Double , lastY :Double , km : Double) : Flow<List<DomainStationNameAndMapXY>>
}