package com.young.cache.repository

import com.young.data.model.DataStationNameAndMapXY
import com.young.cache.dao.LocationDao
import com.young.cache.mapper.CacheToDataMapper.CacheToData
import com.young.cache.mapper.DataToCacheMapper.DataToCache
import com.young.data.datasource.cache.CacheStationCoordinatesDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.sin

class CacheStationCoordinatesRepositoryImpl @Inject constructor(
    private val dao: LocationDao,
    private val dispatcher: CoroutineDispatcher
) : CacheStationCoordinatesDataSource {
    override suspend fun insertStationCoordinateData(items: List<DataStationNameAndMapXY>) : Flow<List<Long>> =
        flowOf(items)
            .map { it.map { it.DataToCache() } }
            .map { dao.insertStationCoordinatesData(it) }
            .flowOn(dispatcher)

    override suspend fun getStationCoordinateAllData(): Flow<List<DataStationNameAndMapXY>> =
        flowOf(dao.getStationCoordinateAllData())
            .map {
                it.map {
                    it.CacheToData()
                }
            }.flowOn(dispatcher)


    override suspend fun getStationCoordinateDataSize(): Flow<Int> =
        flowOf(dao.getStationCoordinateDataSize())
            .flowOn(dispatcher)


    override suspend fun getLocationNearStationList(lastX : Double , lastY :Double , km : Double): Flow<List<DataStationNameAndMapXY>> {
        val cosLat = cos(Math.toRadians(lastX))
        val cosLng = cos(Math.toRadians(lastY))
        val sinLat = sin(Math.toRadians(lastX))
        val sinLng = sin(Math.toRadians(lastY))

        val distanceArea = cos(km/ 6371)

        return flowOf(dao.getNearStationData(cosLat , cosLng , sinLat , sinLng , distanceArea))
            .map {
                it.map {
                    it.CacheToData()
                }
            }.flowOn(dispatcher)
    }
}