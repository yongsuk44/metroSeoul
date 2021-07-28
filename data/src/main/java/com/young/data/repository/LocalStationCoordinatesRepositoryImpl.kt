package com.young.data.repository

import com.young.data.dao.LocationDao
import com.young.data.model.LocalStationNameAndMapXY
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainStationNameAndMapXY
import com.young.domain.repository.information.local.LocalStationCoordinatesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalStationCoordinatesRepositoryImpl @Inject constructor(
    private val dao: LocationDao
) : LocalStationCoordinatesRepository {
    override suspend fun insertStationCoordinateData(items: List<DomainStationNameAndMapXY>) =
        dao.insertStationCoordinatesData(
            items.map(BaseMapper())
        )

    override suspend fun getStationCoordinateAllData(): Flow<List<DomainStationNameAndMapXY>> =
        flowOf(dao.getStationCoordinateAllData())
            .map {
                BaseMapper.setList(BaseMapper<LocalStationNameAndMapXY,DomainStationNameAndMapXY>()).run {
                    this(it)
                }
            }


    override suspend fun getStationCoordinateDataSize(): Flow<Int> =
        flowOf(dao.getStationCoordinateDataSize())

}