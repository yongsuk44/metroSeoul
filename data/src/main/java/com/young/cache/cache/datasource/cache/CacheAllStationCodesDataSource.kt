package com.young.cache.cache.datasource.cache

import com.young.cache.cache.model.DataRow
import kotlinx.coroutines.flow.Flow

interface CacheAllStationCodesDataSource {
    suspend fun insert(items : List<DataRow>) : Flow<List<Long>>
    suspend fun findStationCode(code : String) : Flow<DataRow?>
}