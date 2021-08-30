package com.young.cache.cache.impl.cache

import com.young.cache.cache.datasource.cache.CacheFullRouteInformationDataSource
import com.young.cache.cache.mapper.DataToDomainMapper.DataToDomain
import com.young.cache.cache.mapper.DomainToDataMapper.DomainToData
import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.model.DomainTrailCodeAndLineCode
import com.young.domain.repository.location.CacheFullRouteInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheFullRouteInformationDataSourceImpl @Inject constructor(
    private val dataSource: CacheFullRouteInformationDataSource
) : CacheFullRouteInformationRepository {
    override suspend fun insert(param: List<DomainFullRouteInformationBody>) : Flow<List<Long>> =
        dataSource.insert(param.map { it.DomainToData() })

    override suspend fun insertLineCodeAndTrailCode(param: List<DomainTrailCodeAndLineCode>): Flow<List<Long>> =
        dataSource.insertLineCodeAndTrailCode(param.map { it.DomainToData() })


    override suspend fun getAllData(): Flow<List<DomainFullRouteInformationBody>> =
        dataSource.getAllData().map {
            it.map { it.DataToDomain() }
        }

    override suspend fun getDataSize(): Flow<Int> =
        dataSource.getDataSize()

    override suspend fun getTrailCodeAllData(): Flow<List<DomainTrailCodeAndLineCode>> =
        dataSource.getTrailCodeAllData().map {
            it.map { it.DataToDomain() }
        }

    override suspend fun getStationData(stinCode: List<String>): Flow<List<DomainFullRouteInformationBody>> =
        dataSource.getStationData(stinCode).map {
            it.map { it.DataToDomain() }
        }

    override suspend fun getStationNameToFullRouteInformationData(name: String): Flow<DomainFullRouteInformationBody> =
        dataSource.getStationNameToFullRouteInformationData(name).map {
            it.DataToDomain()
        }
}