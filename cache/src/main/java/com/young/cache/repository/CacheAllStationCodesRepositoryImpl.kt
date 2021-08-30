package com.young.cache.repository

import com.young.cache.dao.AllStationCodeDao
import com.young.cache.cache.datasource.cache.CacheAllStationCodesDataSource
import com.young.cache.mapper.CacheToDataMapper.CacheToData
import com.young.cache.mapper.DataToCacheMapper.DataToCahe
import com.young.cache.cache.model.DataAllStationCodes
import com.young.cache.cache.model.DataRow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheAllStationCodesRepositoryImpl @Inject constructor(
    private val dao: AllStationCodeDao
) : CacheAllStationCodesDataSource {
    override suspend fun insert(items: DataAllStationCodes) {
        dao.insert(
            items.SearchInfoBySubwayNameService.row.map {
                it.DataToCahe()
            }
        )
    }

    override suspend fun findStationCode(code: String): Flow<DataRow?> =
        flowOf(dao.findStationCodes(code)).map {
            it.CacheToData()
        }
}