package com.young.domain.repository

import com.young.domain.model.DomainRow
import kotlinx.coroutines.flow.Flow

interface AllStationCodesRepository {
    suspend fun insert(items : List<DomainRow>) : Flow<List<Long>>
    suspend fun findStationCode(code : String) : Flow<DomainRow?>
}