package com.young.domain.usecase.local

import com.young.domain.model.DomainStationNameAndMapXY
import com.young.domain.repository.location.LocalStationCoordinatesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalStationCoordinateBaseUseCase {
    suspend fun insertStationCoordinateData(items : List<DomainStationNameAndMapXY>)
    suspend fun getStationCoordinateAllData() : Flow<List<DomainStationNameAndMapXY>>
    suspend fun getStationCoordinateDataSize() : Flow<Int>
    suspend fun getLocationNearStationList(lastX : Double , lastY :Double , km : Double) : Flow<List<DomainStationNameAndMapXY>>
}

class LocalStationCoordinateUseCase @Inject constructor(
    private val local : LocalStationCoordinatesRepository
) : LocalStationCoordinateBaseUseCase {
    override suspend fun insertStationCoordinateData(items: List<DomainStationNameAndMapXY>) {
        local.insertStationCoordinateData(items)
    }

    override suspend fun getStationCoordinateAllData(): Flow<List<DomainStationNameAndMapXY>> =
        local.getStationCoordinateAllData()

    override suspend fun getStationCoordinateDataSize(): Flow<Int> =
        local.getStationCoordinateDataSize()

    override suspend fun getLocationNearStationList(lastX : Double , lastY :Double , km : Double): Flow<List<DomainStationNameAndMapXY>> =
        local.getLocationNearStationList(lastX, lastY, km)

}