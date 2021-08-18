package com.young.domain.repository.location

import com.young.domain.model.DomainAllStationCodes
import com.young.domain.model.Row
import kotlinx.coroutines.flow.Flow

interface LocalAllStationCodesRepository {
    suspend fun insert(items : DomainAllStationCodes)
    suspend fun findStationCode(code : String) : Flow<Row?>
}