package com.young.domain.usecase

import com.young.domain.model.*
import com.young.domain.repository.FullRouteInformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FullRouteInformationBaseUseCase {
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

class FullRouteInformationUseCase @Inject constructor(
    private val repository: FullRouteInformationRepository
) : FullRouteInformationBaseUseCase {
    override suspend fun insert(param: List<DomainFullRouteInformationBody>): Flow<List<Long>> =
        repository.insert(param)

    override suspend fun insertLineCodeAndTrailCode(param: List<DomainTrailCodeAndLineCode>): Flow<List<Long>> =
        repository.insertLineCodeAndTrailCode(param)

    override suspend fun readDataSize(): Flow<Int> =
        repository.readDataSize()

    override suspend fun readStationNameToFullRouteInformationData(name: String): Flow<DomainFullRouteInformationBody> =
        repository.readStationNameToFullRouteInformationData(name)

    override suspend fun getConvenienceInformation(key: String, lineCode: String, trailCode: String, stationCode: String): Flow<DomainConvenienceInformation> =
        repository.getConvenienceInformation(key, lineCode, trailCode, stationCode)

    override suspend fun getAllStationCode(seoulKey: String): Flow<List<DomainRow>> =
        repository.getAllStationCode(seoulKey)

    override suspend fun getStationEntranceData(key: String, railCode: String, lineCd: String, stinCode: String): Flow<DomainStationEntrance> =
        repository.getStationEntranceData(key, railCode, lineCd, stinCode)

    override suspend fun readTrailCodeAllData(): Flow<List<DomainTrailCodeAndLineCode>> =
        repository.readTrailCodeAllData()

    override suspend fun readStationData(stinCode: List<String>): Flow<List<DomainFullRouteInformationBody>> =
        repository.readStationData(stinCode)

    override suspend fun findStationRouteInformation(key: String?): Flow<List<DomainFullRouteInformationBody>> =
        repository.findStationRouteInformation(key)
}