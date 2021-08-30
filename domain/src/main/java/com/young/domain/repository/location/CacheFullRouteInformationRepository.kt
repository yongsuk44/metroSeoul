package com.young.domain.repository.location

import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.model.DomainTrailCodeAndLineCode
import kotlinx.coroutines.flow.Flow

interface CacheFullRouteInformationRepository {
    suspend fun insert(param : List<DomainFullRouteInformationBody>) : Flow<List<Long>>
    suspend fun insertLineCodeAndTrailCode(param : List<DomainTrailCodeAndLineCode>): Flow<List<Long>>
    suspend fun getAllData() : Flow<List<DomainFullRouteInformationBody>>
    suspend fun getDataSize() : Flow<Int>
    suspend fun getTrailCodeAllData() : Flow<List<DomainTrailCodeAndLineCode>>
    suspend fun getStationData(stinCode : List<String>) : Flow<List<DomainFullRouteInformationBody>>
    suspend fun getStationNameToFullRouteInformationData(name : String) : Flow<DomainFullRouteInformationBody>
}