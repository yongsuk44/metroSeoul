package com.young.domain.usecase

import com.young.domain.model.DomainStationNameAndMapXY
import com.young.domain.repository.StationCoordinatesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CoordinateBaseUseCase {
    suspend fun insertStationCoordinateData(items : List<DomainStationNameAndMapXY>) : Flow<List<Long>>
    suspend fun getStationCoordinateAllData() : Flow<List<DomainStationNameAndMapXY>>
    suspend fun getStationCoordinateDataSize() : Flow<Int>
    suspend fun getLocationNearStationList(lastX : Double , lastY :Double , km : Double) : Flow<List<DomainStationNameAndMapXY>>
}

class CoordinateUseCase @Inject constructor(
    private val repository : StationCoordinatesRepository
) : CoordinateBaseUseCase {
    override suspend fun insertStationCoordinateData(items: List<DomainStationNameAndMapXY>) : Flow<List<Long>> =
        repository.insertStationCoordinateData(items)

    override suspend fun getStationCoordinateAllData(): Flow<List<DomainStationNameAndMapXY>> =
        repository.getStationCoordinateAllData()

    override suspend fun getStationCoordinateDataSize(): Flow<Int> =
        repository.getStationCoordinateDataSize()

    override suspend fun getLocationNearStationList(lastX : Double , lastY :Double , km : Double): Flow<List<DomainStationNameAndMapXY>> =
        repository.getLocationNearStationList(lastX, lastY, km)

}