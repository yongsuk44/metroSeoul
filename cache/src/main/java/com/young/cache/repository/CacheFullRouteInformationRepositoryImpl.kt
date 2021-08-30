package com.young.cache.repository

import android.os.Looper
import com.young.cache.cache.datasource.cache.CacheFullRouteInformationDataSource
import com.young.cache.cache.model.DataFullRouteInformationBody
import com.young.cache.cache.model.DataTrailCodeAndLineCode
import com.young.cache.dao.FullRouteInformationDao
import com.young.cache.mapper.CacheToDataMapper.CacheToData
import com.young.cache.mapper.DataToCacheMapper.DataToCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheFullRouteInformationRepositoryImpl @Inject constructor(
    private val dao: FullRouteInformationDao
) : CacheFullRouteInformationDataSource {
    override suspend fun insert(param: List<DataFullRouteInformationBody>) : Flow<List<Long>> =
        flowOf(param)
            .map { it.map { it.DataToCache() } }
            .map { dao.insertFullRouteInformation(it) }
            .flowOn(Dispatchers.IO)


    override suspend fun insertLineCodeAndTrailCode(param: List<DataTrailCodeAndLineCode>) : Flow<List<Long>> =
        flowOf(param)
            .map { it.map { it.DataToCache() } }
            .map { dao.insertLineCodeAndTrailCode(it) }
            .flowOn(Dispatchers.IO)


    override suspend fun getAllData(): Flow<List<DataFullRouteInformationBody>> =
        flowOf(dao.getAllData())
            .map { it.map { it.CacheToData() } }
            .flowOn(Dispatchers.IO)

    override suspend fun getDataSize(): Flow<Int> =
        flowOf(dao.getDataSize())
            .flowOn(Dispatchers.IO)

    override suspend fun getTrailCodeAllData(): Flow<List<DataTrailCodeAndLineCode>> =
        flowOf(dao.getTrailCodeAllData())
            .map { it.map { it.CacheToData() } }
            .flowOn(Dispatchers.IO)

    override suspend fun getStationData(stinCode : List<String>): Flow<List<DataFullRouteInformationBody>> =
        flowOf(stinCode)
            .map { dao.getStationData(it) }
            .map { it.map { it.CacheToData() } }
            .flowOn(Dispatchers.IO)

    override suspend fun getStationNameToFullRouteInformationData(name: String): Flow<DataFullRouteInformationBody> =
        flowOf(name)
            .map { dao.getStationNameToFullRouteInformationData(it) }
            .map { it.CacheToData() }
            .flowOn(Dispatchers.IO)

}