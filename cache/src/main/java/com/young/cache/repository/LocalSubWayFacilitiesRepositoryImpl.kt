package com.young.cache.repository

import com.young.cache.dao.SubWayFacilitiesDao
import com.young.cache.model.CacheSubwayFacilities
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.subwayfacilities.CacheSubWayFacilitiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CacheSubWayFacilitiesRepositoryImpl @Inject constructor(
    private val service: SubWayFacilitiesDao
) : CacheSubWayFacilitiesRepository {
    override suspend fun getLineMetroData(lineNumber: String): Flow<List<DomainSubwayFacilities>> =
        flowOf(service.getLineMetroData(lineNumber))
            .map {
                BaseMapper.setList(BaseMapper<CacheSubwayFacilities, DomainSubwayFacilities>())
                    .run {
                        this(it)
                    }
            }
            .flowOn(Dispatchers.IO)

    override suspend fun insertSubWayFacilitiesDataAll(items: List<DomainSubwayFacilities>) {
        BaseMapper.setList(BaseMapper<DomainSubwayFacilities, CacheSubwayFacilities>()).run {
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
        BaseMapper.setList(BaseMapper<DomainSubwayFacilities, CacheSubwayFacilities>()).run {
            service.update(
                this(items)
            )
        }
    }

    override suspend fun getDataSize(): Int = service.getDataSize()

    override suspend fun getCacheAllData(): Flow<List<DomainSubwayFacilities>> =
        flowOf(service.getAllData())
            .map {
                BaseMapper.setList(BaseMapper<CacheSubwayFacilities, DomainSubwayFacilities>())
                    .run {
                        this(it)
                    }
            }.flowOn(Dispatchers.IO)
}