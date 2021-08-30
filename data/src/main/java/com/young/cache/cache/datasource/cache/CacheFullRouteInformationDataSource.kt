package com.young.cache.cache.datasource.cache

import com.young.cache.cache.model.DataFullRouteInformationBody
import com.young.cache.cache.model.DataTrailCodeAndLineCode
import kotlinx.coroutines.flow.Flow

interface CacheFullRouteInformationDataSource {
    suspend fun insert(param : List<DataFullRouteInformationBody>) : Flow<List<Long>>
    suspend fun insertLineCodeAndTrailCode(param : List<DataTrailCodeAndLineCode>): Flow<List<Long>>
    suspend fun getAllData() : Flow<List<DataFullRouteInformationBody>>
    suspend fun getDataSize() : Flow<Int>
    suspend fun getTrailCodeAllData() : Flow<List<DataTrailCodeAndLineCode>>
    suspend fun getStationData(stinCode : List<String>) : Flow<List<DataFullRouteInformationBody>>
    suspend fun getStationNameToFullRouteInformationData(name : String) : Flow<DataFullRouteInformationBody>
}