package com.young.cache.repository

import com.young.cache.cache.datasource.cache.CacheStationCoordinatesDataSource
import com.young.cache.cache.model.DataStationNameAndMapXY
import com.young.cache.dao.LocationDao
import com.young.cache.mapper.CacheToDataMapper.CacheToData
import com.young.cache.mapper.DataToCacheMapper.DataToCache
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.sin

class CacheStationCoordinatesRepositoryImpl @Inject constructor(
    private val dao: LocationDao
) : CacheStationCoordinatesDataSource {
    override suspend fun insertStationCoordinateData(items: List<DataStationNameAndMapXY>) =
        dao.insertStationCoordinatesData(
            items.map {
                it.DataToCache()
            }
        )

    override suspend fun getStationCoordinateAllData(): Flow<List<DataStationNameAndMapXY>> =
        flowOf(dao.getStationCoordinateAllData())
            .map {
                it.map {
                    it.CacheToData()
                }
            }


    override suspend fun getStationCoordinateDataSize(): Flow<Int> =
        flowOf(dao.getStationCoordinateDataSize())


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
            }
    }
}