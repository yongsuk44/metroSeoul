package com.young.domain.repository.information.local

import com.young.domain.model.AllRouteInformation
import com.young.domain.model.DomainTrailCodeAndLineCode
import kotlinx.coroutines.flow.Flow

interface LocalFullRouteInformationRepository {
    suspend fun insert(param : List<AllRouteInformation>)
    suspend fun getAllData() : Flow<List<AllRouteInformation>>
    suspend fun getDataSize() : Flow<Int>
    suspend fun getTrailCodeAllData() : Flow<List<DomainTrailCodeAndLineCode>>
    suspend fun getStationData(stinCode : List<String>) : Flow<List<AllRouteInformation>>
    suspend fun getStationNameToFullRouteInformationData(name : String) : Flow<AllRouteInformation>
}