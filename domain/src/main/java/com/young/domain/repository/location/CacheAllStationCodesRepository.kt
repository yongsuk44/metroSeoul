package com.young.domain.repository.location

import com.young.domain.model.DomainRow
import kotlinx.coroutines.flow.Flow

interface CacheAllStationCodesRepository {
    suspend fun insert(items : List<DomainRow>) : Flow<List<Long>>
    suspend fun findStationCode(code : String) : Flow<DomainRow?>
}