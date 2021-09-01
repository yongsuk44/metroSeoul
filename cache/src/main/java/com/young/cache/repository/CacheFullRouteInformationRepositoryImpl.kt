package com.young.cache.repository

import com.young.data.model.DataFullRouteInformationBody
import com.young.data.model.DataTrailCodeAndLineCode
import com.young.cache.dao.FullRouteInformationDao
import com.young.cache.mapper.CacheToDataMapper.CacheToData
import com.young.cache.mapper.DataToCacheMapper.DataToCache
import com.young.data.datasource.cache.CacheFullRouteInformationDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CacheFullRouteInformationRepositoryImpl @Inject constructor(
    private val dao: FullRouteInformationDao ,
    private val dispatcher: CoroutineDispatcher
) : CacheFullRouteInformationDataSource {
        override suspend fun insert(param: List<DataFullRouteInformationBody>) : Flow<List<Long>> =
        flowOf(param)
            .map { it.map { it.DataToCache() } }
            .map { dao.insertFullRouteInformation(it) }
            .flowOn(dispatcher)

    override suspend fun insertLineCodeAndTrailCode(param: List<DataTrailCodeAndLineCode>): Flow<List<Long>> =
        flowOf(param)
            .map { it.map { it.DataToCache() } }
            .map { dao.insertLineCodeAndTrailCode(it) }
            .flowOn(dispatcher)

    override suspend fun getAllData(): Flow<List<DataFullRouteInformationBody>> =
        flowOf(dao.getAllData())
            .map { it.map { it.CacheToData() } }
            .flowOn(dispatcher)

    override suspend fun getDataSize(): Flow<Int> =
        flowOf(dao.getDataSize())
            .flowOn(dispatcher)

    override suspend fun getTrailCodeAllData(): Flow<List<DataTrailCodeAndLineCode>> =
        flowOf(dao.getTrailCodeAllData())
            .map { it.map { it.CacheToData() } }
            .flowOn(dispatcher)

    override suspend fun getStationData(stinCode: List<String>): Flow<List<DataFullRouteInformationBody>> =
        flowOf(stinCode)
            .map { dao.getStationData(it) }
            .map { it.map { it.CacheToData() } }
            .flowOn(dispatcher)

    override suspend fun getStationNameToFullRouteInformationData(name: String): Flow<DataFullRouteInformationBody> =
        flowOf(name)
            .map { dao.getStationNameToFullRouteInformationData(it) }
            .map { it.CacheToData() }
            .flowOn(dispatcher)

}