package com.young.data.impl

import com.young.data.datasource.cache.CacheStationCoordinatesDataSource
import com.young.data.mapper.DataToDomainMapper.DataToDomain
import com.young.data.mapper.DomainToDataMapper.DomainToData
import com.young.domain.model.DomainStationNameAndMapXY
import com.young.domain.repository.StationCoordinatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoordinatesDataSourceImpl constructor(
    private val cache: CacheStationCoordinatesDataSource
) : StationCoordinatesRepository {
    override suspend fun insertStationCoordinateData(items: List<DomainStationNameAndMapXY>) : Flow<List<Long>> =
        cache.insertStationCoordinateData(items.map { it.DomainToData() })

    override suspend fun getStationCoordinateAllData(): Flow<List<DomainStationNameAndMapXY>> =
        cache.getStationCoordinateAllData().map { it.map { it.DataToDomain() } }

    override suspend fun getStationCoordinateDataSize(): Flow<Int> =
        cache.getStationCoordinateDataSize()

    override suspend fun getLocationNearStationList(
        lastX: Double,
        lastY: Double,
        km: Double
    ): Flow<List<DomainStationNameAndMapXY>> =
        cache.getLocationNearStationList(lastX, lastY, km).map { it.map { it.DataToDomain() } }
}