package com.young.domain.usecase

import com.young.domain.model.DomainStationNameAndMapXY
import com.young.domain.repository.location.CacheStationCoordinatesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CacheCoordinateBaseUseCase {
    suspend fun insertStationCoordinateData(items : List<DomainStationNameAndMapXY>) : Flow<List<Long>>
    suspend fun getStationCoordinateAllData() : Flow<List<DomainStationNameAndMapXY>>
    suspend fun getStationCoordinateDataSize() : Flow<Int>
    suspend fun getLocationNearStationList(lastX : Double , lastY :Double , km : Double) : Flow<List<DomainStationNameAndMapXY>>
}

class CoordinateUseCase @Inject constructor(
    private val cache : CacheStationCoordinatesRepository
) : CacheCoordinateBaseUseCase {
    override suspend fun insertStationCoordinateData(items: List<DomainStationNameAndMapXY>) : Flow<List<Long>> =
        cache.insertStationCoordinateData(items)

    override suspend fun getStationCoordinateAllData(): Flow<List<DomainStationNameAndMapXY>> =
        cache.getStationCoordinateAllData()

    override suspend fun getStationCoordinateDataSize(): Flow<Int> =
        cache.getStationCoordinateDataSize()

    override suspend fun getLocationNearStationList(lastX : Double , lastY :Double , km : Double): Flow<List<DomainStationNameAndMapXY>> =
        cache.getLocationNearStationList(lastX, lastY, km)

}