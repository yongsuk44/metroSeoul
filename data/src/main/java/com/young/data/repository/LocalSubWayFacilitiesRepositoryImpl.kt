package com.young.data.repository

import com.young.data.dao.SubWayFacilitiesDao
import com.young.data.model.LocalSubwayFacilities
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LocalSubWayFacilitiesRepositoryImpl @Inject constructor(
    private val service: SubWayFacilitiesDao
) : LocalSubWayFacilitiesRepository {
    override suspend fun getLineMetroData(lineNumber: String): Flow<List<DomainSubwayFacilities>> =
        flowOf(service.getLineMetroData(lineNumber))
            .map {
                BaseMapper.setList(BaseMapper<LocalSubwayFacilities, DomainSubwayFacilities>())
                    .run {
                        this(it)
                    }
            }
            .flowOn(Dispatchers.IO)

    override suspend fun insertSubWayFacilitiesDataAll(items: List<DomainSubwayFacilities>) {
        BaseMapper.setList(BaseMapper<DomainSubwayFacilities, LocalSubwayFacilities>()).run {
            service.insertSubWayFacilitiesData(
                this(items)
            )
        }
    }

    override suspend fun getStationData(stationNumber: String): Flow<DomainSubwayFacilities> =
        flow {
            service.getStationData(stationNumber)
        }

    override suspend fun updateSubWayFacilitiesData(items: List<DomainSubwayFacilities>) {
        BaseMapper.setList(BaseMapper<DomainSubwayFacilities, LocalSubwayFacilities>()).run {
            service.update(
                this(items)
            )
        }
    }

    override suspend fun getDataSize(): Int = service.getDataSize()

    override suspend fun getLocalAllData(): Flow<List<DomainSubwayFacilities>> =
        flowOf(service.getAllData())
            .map {
                BaseMapper.setList(BaseMapper<LocalSubwayFacilities, DomainSubwayFacilities>())
                    .run {
                        this(it)
                    }
            }.flowOn(Dispatchers.IO)
}