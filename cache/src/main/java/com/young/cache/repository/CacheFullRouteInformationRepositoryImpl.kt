package com.young.cache.repository

import com.young.cache.dao.FullRouteInformationDao
import com.young.cache.cache.datasource.cache.CacheFullRouteInformationDataSource
import com.young.cache.mapper.CacheToDataMapper.CacheToData
import com.young.cache.mapper.DataToCacheMapper.DataToCache
import com.young.cache.cache.model.DataFullRouteInformationBody
import com.young.cache.cache.model.DataTrailCodeAndLineCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheFullRouteInformationRepositoryImpl @Inject constructor(
    private val dao: FullRouteInformationDao
) : CacheFullRouteInformationDataSource {
    override suspend fun insert(param: List<DataFullRouteInformationBody>) {
        dao.insertFullRouteInformation(param.map { it.DataToCache() })
    }

    override suspend fun insertLineCodeAndTrailCode(param: List<DataTrailCodeAndLineCode>) {
        dao.insertLineCodeAndTrailCode(param.map { it.DataToCache() })
    }

    override suspend fun getAllData(): Flow<List<DataFullRouteInformationBody>> =
        flowOf(dao.getAllData())
            .map {
                it.map { it.CacheToData() }
            }

    override suspend fun getDataSize(): Flow<Int> =
        flowOf(dao.getDataSize())

    override suspend fun getTrailCodeAllData(): Flow<List<DataTrailCodeAndLineCode>> =
        flowOf(dao.getTrailCodeAllData())
            .map {
                it.map { it.CacheToData() }
            }

    override suspend fun getStationData(stinCode : List<String>): Flow<List<DataFullRouteInformationBody>> =
        flowOf(dao.getStationData(stinCode))
            .map {
                it.map { it.CacheToData() }
            }

    override suspend fun getStationNameToFullRouteInformationData(name: String): Flow<DataFullRouteInformationBody> =
        flowOf(dao.getStationNameToFullRouteInformationData(name))
            .map {
                it.CacheToData()
            }

}