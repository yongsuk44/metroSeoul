package com.young.domain.repository.location

import com.young.domain.model.DomainFullRouteInformation
import com.young.domain.model.DomainTrailCodeAndLineCode
import kotlinx.coroutines.flow.Flow

interface CacheFullRouteInformationRepository {
    suspend fun insert(param : List<DomainFullRouteInformation>)
    suspend fun getAllData() : Flow<List<DomainFullRouteInformation>>
    suspend fun getDataSize() : Flow<Int>
    suspend fun getTrailCodeAllData() : Flow<List<DomainTrailCodeAndLineCode>>
    suspend fun getStationData(stinCode : List<String>) : Flow<List<DomainFullRouteInformation>>
    suspend fun getStationNameToFullRouteInformationData(name : String) : Flow<DomainFullRouteInformation>
}