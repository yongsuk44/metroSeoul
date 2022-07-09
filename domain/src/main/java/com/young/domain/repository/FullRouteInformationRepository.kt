package com.young.domain.repository

import com.young.domain.model.*
import kotlinx.coroutines.flow.Flow

interface FullRouteInformationRepository {
    // local
    suspend fun insert(param : List<DomainFullRouteInformationBody>) : Flow<List<Long>>
    suspend fun insertLineCodeAndTrailCode(param : List<DomainTrailCodeAndLineCode>): Flow<List<Long>>
    suspend fun readDataSize() : Flow<Int>
    suspend fun readTrailCodeAllData() : Flow<List<DomainTrailCodeAndLineCode>>
    suspend fun readStationData(stinCode : List<String>) : Flow<List<DomainFullRouteInformationBody>>
    suspend fun readStationNameToFullRouteInformationData(name : String) : Flow<DomainFullRouteInformationBody>

    // remote
    suspend fun getConvenienceInformation(key: String, lineCode: String, trailCode: String, stationCode: String) : Flow<DomainConvenienceInformation>
    suspend fun getAllStationCode(seoulKey: String) : Flow<List<DomainRow>>
    suspend fun getStationEntranceData(key : String , railCode : String ,lineCd : String, stinCode : String) : Flow<DomainStationEntrance>

    // local && remote
    suspend fun findStationRouteInformation(key : String?) : Flow<List<DomainFullRouteInformationBody>>
}