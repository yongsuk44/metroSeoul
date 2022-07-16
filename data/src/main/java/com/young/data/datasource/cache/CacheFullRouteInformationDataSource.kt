package com.young.data.datasource.cache

import com.young.data.model.DataFullRouteInformationBody
import com.young.data.model.DataTrailCodeAndLineCode
import com.young.domain.model.DomainFullRouteInformationBody
import kotlinx.coroutines.flow.Flow

interface CacheFullRouteInformationDataSource {
    suspend fun insert(param : List<DataFullRouteInformationBody>) : Flow<List<Long>>
    suspend fun insertLineCodeAndTrailCode(param : List<DataTrailCodeAndLineCode>): Flow<List<Long>>
    suspend fun readDataSize() : Flow<Int>
    suspend fun readTrailCodeAllData() : Flow<List<DataTrailCodeAndLineCode>>
    suspend fun readStationData(stinCode : List<String>) : Flow<List<DataFullRouteInformationBody>>
    suspend fun readStationNameToFullRouteInformationData(name : String) : Flow<DataFullRouteInformationBody>

    suspend fun findStationRouteInformation(key : String?) : Flow<List<DataFullRouteInformationBody>>
}