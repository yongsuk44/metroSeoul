package com.young.data.repository

import com.young.data.dao.LocationDao
import com.young.domain.model.DomainStationNameAndMapXY
import com.young.domain.repository.location.CacheStationCoordinatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.cos
import kotlin.math.sin

class CacheStationCoordinatesRepositoryImpl @Inject constructor(
    private val dao: LocationDao
) : CacheStationCoordinatesRepository {
    override suspend fun insertStationCoordinateData(items: List<DomainStationNameAndMapXY>) =
        dao.insertStationCoordinatesData(
            items.map {
                it.DomainToLocal()
            }
        )

    override suspend fun getStationCoordinateAllData(): Flow<List<DomainStationNameAndMapXY>> =
        flowOf(dao.getStationCoordinateAllData())
            .map {
                it.map {
                    it.LocalToDomain()
                }
            }


    override suspend fun getStationCoordinateDataSize(): Flow<Int> =
        flowOf(dao.getStationCoordinateDataSize())


    override suspend fun getLocationNearStationList(lastX : Double , lastY :Double , km : Double): Flow<List<DomainStationNameAndMapXY>> {
        val cosLat = cos(Math.toRadians(lastX))
        val cosLng = cos(Math.toRadians(lastY))
        val sinLat = sin(Math.toRadians(lastX))
        val sinLng = sin(Math.toRadians(lastY))

        val distanceArea = cos(km/ 6371)

        return flowOf(dao.getNearStationData(cosLat , cosLng , sinLat , sinLng , distanceArea))
            .map {
                it.map {
                    it.LocalToDomain()
                }
            }
    }
}