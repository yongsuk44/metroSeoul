package com.young.domain.usecase

import com.young.domain.model.*
import com.young.domain.repository.location.CacheFullRouteInformationRepository
import com.young.domain.repository.remote.RemoteFullRouteInformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CacheFullRouteInformationBaseUseCase {
    suspend fun insert(param: List<DomainFullRouteInformationBody>): Flow<List<Long>>
    suspend fun insertLineCodeAndTrailCode(param : List<DomainTrailCodeAndLineCode>): Flow<List<Long>>
    suspend fun getAllData(): Flow<List<DomainFullRouteInformationBody>>
    suspend fun getDataSize(): Flow<Int>
    suspend fun getStationNameToFullRouteInformationData(name: String): Flow<DomainFullRouteInformationBody>
}

interface RemoteFullRouteInformationBaseUseCase {
    suspend fun getStationRouteInformation(key: String): Flow<List<DomainFullRouteInformationBody>>
    suspend fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    ): Flow<DomainConvenienceInformation>

    suspend fun getAllStationCode(seoulKey: String): Flow<List<DomainRow>>
    suspend fun getStationEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ): Flow<DomainStationEntrance>
}

class FullRouteInformationUseCase @Inject constructor(
    private val remote: RemoteFullRouteInformationRepository,
    private val cache: CacheFullRouteInformationRepository
) : RemoteFullRouteInformationBaseUseCase, CacheFullRouteInformationBaseUseCase {
    override suspend fun insert(param: List<DomainFullRouteInformationBody>): Flow<List<Long>> =
        cache.insert(param)

    override suspend fun insertLineCodeAndTrailCode(param: List<DomainTrailCodeAndLineCode>): Flow<List<Long>> =
        cache.insertLineCodeAndTrailCode(param)


    override suspend fun getAllData(): Flow<List<DomainFullRouteInformationBody>> =
        cache.getAllData()

    override suspend fun getDataSize(): Flow<Int> =
        cache.getDataSize()

    override suspend fun getStationNameToFullRouteInformationData(name: String): Flow<DomainFullRouteInformationBody> =
        cache.getStationNameToFullRouteInformationData(name)


    override suspend fun getStationRouteInformation(
        key: String
    ): Flow<List<DomainFullRouteInformationBody>> =
        remote.getStationRouteInformation(key)

    override suspend fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    ): Flow<DomainConvenienceInformation> =
        remote.getConvenienceInformation(key, lineCode, trailCode, stationCode)

    override suspend fun getAllStationCode(seoulKey: String): Flow<List<DomainRow>> =
        remote.getAllStationCode(seoulKey)

    override suspend fun getStationEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ): Flow<DomainStationEntrance> =
        remote.getStationEntranceData(key, railCode, lineCd, stinCode)

}