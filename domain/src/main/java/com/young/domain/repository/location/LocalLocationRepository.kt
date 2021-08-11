package com.young.domain.repository.location

import com.young.domain.model.DomainTrailCodeAndLineCode
import kotlinx.coroutines.flow.Flow

interface LocalLocationRepository {
    suspend fun getTrailCodeAndLineCode() : Flow<List<DomainTrailCodeAndLineCode>>
}