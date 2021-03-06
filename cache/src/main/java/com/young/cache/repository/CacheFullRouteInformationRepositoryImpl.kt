package com.young.cache.repository

import com.young.cache.dao.FullRouteInformationDao
import com.young.cache.mapper.CacheToDataMapper.CacheToData
import com.young.cache.mapper.DataToCacheMapper.DataToCache
import com.young.data.datasource.cache.CacheFullRouteInformationDataSource
import com.young.data.model.DataFullRouteInformationBody
import com.young.data.model.DataTrailCodeAndLineCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheFullRouteInformationRepositoryImpl @Inject constructor(
    private val dao: FullRouteInformationDao
) : CacheFullRouteInformationDataSource {
    override suspend fun insert(param: List<DataFullRouteInformationBody>): Flow<List<Long>> =
        flowOf(param)
            .map { it.map { it.DataToCache() } }
            .map { dao.insertFullRouteInformation(it) }

    override suspend fun insertLineCodeAndTrailCode(param: List<DataTrailCodeAndLineCode>): Flow<List<Long>> =
        flowOf(param)
            .map { it.map { it.DataToCache() } }
            .map { dao.insertLineCodeAndTrailCode(it) }

    override suspend fun readDataSize(): Flow<Int> =
        flowOf(dao.getDataSize())

    override suspend fun readTrailCodeAllData(): Flow<List<DataTrailCodeAndLineCode>> =
        flowOf(dao.getTrailCodeAllData())
            .map { it.map { it.CacheToData() } }

    override suspend fun readStationData(stinCode: List<String>): Flow<List<DataFullRouteInformationBody>> =
        flowOf(stinCode)
            .map { dao.getStationData(it) }
            .map { it.map { it.CacheToData() } }

    override suspend fun readStationNameToFullRouteInformationData(name: String): Flow<DataFullRouteInformationBody> =
        flowOf(name)
            .map { dao.getStationNameToFullRouteInformationData(it) }
            .map { it.CacheToData() }

    override suspend fun findStationRouteInformation(key: String?): Flow<List<DataFullRouteInformationBody>> =
        flowOf(dao.getAllData())
            .map { it.map { it.CacheToData() } }

}