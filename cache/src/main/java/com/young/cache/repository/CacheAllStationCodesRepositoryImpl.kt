package com.young.cache.repository

import com.young.cache.dao.AllStationCodeDao
import com.young.cache.mapper.CacheToDataMapper.CacheToData
import com.young.cache.mapper.DataToCacheMapper.DataToCache
import com.young.data.datasource.cache.CacheAllStationCodesDataSource
import com.young.data.model.DataRow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheAllStationCodesRepositoryImpl @Inject constructor(
    private val dao: AllStationCodeDao
) : CacheAllStationCodesDataSource {

    override suspend fun insert(items: List<DataRow>): Flow<List<Long>> =
        flowOf(items)
            .map { it.map { it.DataToCache() } }
            .map { dao.insert(it) }

    override suspend fun findStationCode(code: String): Flow<DataRow?> =
        flowOf(code)
            .map { dao.findStationCodes(code) }
            .map { it.CacheToData() }

}