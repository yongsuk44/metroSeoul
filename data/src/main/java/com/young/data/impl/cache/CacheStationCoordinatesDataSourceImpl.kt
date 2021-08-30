package com.young.data.impl.cache

import com.young.cache.cache.mapper.DataToDomainMapper.DataToDomain
import com.young.cache.cache.mapper.DomainToDataMapper.DomainToData
import com.young.domain.model.DomainStationNameAndMapXY
import com.young.domain.repository.location.CacheStationCoordinatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheStationCoordinatesDataSourceImpl @Inject constructor(
    private val dataSource: com.young.data.datasource.cache.CacheStationCoordinatesDataSource
) : CacheStationCoordinatesRepository {
    override suspend fun insertStationCoordinateData(items: List<DomainStationNameAndMapXY>) =
        dataSource.insertStationCoordinateData(items.map { it.DomainToData() })

    override suspend fun getStationCoordinateAllData(): Flow<List<DomainStationNameAndMapXY>> =
        dataSource.getStationCoordinateAllData().map {
            it.map { it.DataToDomain() }
        }

    override suspend fun getStationCoordinateDataSize(): Flow<Int> =
        dataSource.getStationCoordinateDataSize()

    override suspend fun getLocationNearStationList(
        lastX: Double,
        lastY: Double,
        km: Double
    ): Flow<List<DomainStationNameAndMapXY>> =
        dataSource.getLocationNearStationList(lastX, lastY, km).map {
            it.map { it.DataToDomain() }
        }
}