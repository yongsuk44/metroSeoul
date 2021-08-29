package com.young.domain.repository.location

import com.young.domain.model.DomainAllStationCodes
import com.young.domain.model.DomainRow
import kotlinx.coroutines.flow.Flow

interface CacheAllStationCodesRepository {
    suspend fun insert(items : DomainAllStationCodes)
    suspend fun findStationCode(code : String) : Flow<DomainRow?>
}