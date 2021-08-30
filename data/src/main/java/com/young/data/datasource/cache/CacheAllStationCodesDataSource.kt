package com.young.data.datasource.cache

import com.young.data.model.DataRow
import kotlinx.coroutines.flow.Flow

interface CacheAllStationCodesDataSource {
    suspend fun insert(items : List<DataRow>) : Flow<List<Long>>
    suspend fun findStationCode(code : String) : Flow<DataRow?>
}