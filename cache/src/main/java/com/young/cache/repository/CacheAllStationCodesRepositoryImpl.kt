package com.young.cache.repository

import android.os.Looper
import com.young.cache.cache.datasource.cache.CacheAllStationCodesDataSource
import com.young.cache.cache.model.DataRow
import com.young.cache.dao.AllStationCodeDao
import com.young.cache.mapper.CacheToDataMapper.CacheToData
import com.young.cache.mapper.DataToCacheMapper.DataToCahe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CacheAllStationCodesRepositoryImpl @Inject constructor(
    private val dao: AllStationCodeDao
) : CacheAllStationCodesDataSource {

    override suspend fun insert(items: List<DataRow>): Flow<List<Long>> =
        flowOf(items)
            .map { it.map { it.DataToCahe() } }
            .map { dao.insert(it) }
            .flowOn(Dispatchers.IO)

    override suspend fun findStationCode(code: String): Flow<DataRow?> =
        flowOf(code)
            .map { dao.findStationCodes(code) }
            .map { it.CacheToData() }
            .flowOn(Dispatchers.IO)

}