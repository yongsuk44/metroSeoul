package com.young.cache.cache.datasource.cache

import com.young.cache.cache.model.DataAllStationCodes
import com.young.cache.cache.model.DataRow
import kotlinx.coroutines.flow.Flow

interface CacheAllStationCodesDataSource {
    suspend fun insert(items : DataAllStationCodes)
    suspend fun findStationCode(code : String) : Flow<DataRow?>
}