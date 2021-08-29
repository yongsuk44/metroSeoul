package com.young.domain.usecase

import com.young.domain.model.*
import com.young.domain.repository.location.CacheFullRouteInformationRepository
import com.young.domain.repository.remote.RemoteFullRouteInformationRepository
import com.young.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

interface CacheFullRouteInformationBaseUseCase {
    suspend fun insert(param: List<DomainFullRouteInformation>): Flow<Unit>
    suspend fun getAllData(): Flow<List<DomainFullRouteInformation>>
    suspend fun getDataSize(): Flow<Int>
    suspend fun getStationNameToFullRouteInformationData(name: String): Flow<DomainFullRouteInformation>
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
    suspend fun getPlatformEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ): Flow<DomainPlatformEntrance>
}

class FullRouteInformationUseCase @Inject constructor(
    private val remote: RemoteFullRouteInformationRepository,
    private val cache: CacheFullRouteInformationRepository
) : RemoteFullRouteInformationBaseUseCase, CacheFullRouteInformationBaseUseCase {
    override suspend fun insert(param: List<DomainFullRouteInformation>): Flow<Unit> =
        flowOf(cache.insert(param))

    override suspend fun getAllData(): Flow<List<DomainFullRouteInformation>> =
        cache.getAllData()

    override suspend fun getDataSize(): Flow<Int> =
        cache.getDataSize()

    override suspend fun getStationNameToFullRouteInformationData(name: String): Flow<DomainFullRouteInformation> =
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

    override suspend fun getPlatformEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ): Flow<DomainPlatformEntrance> =
        remote.getPlatformEntranceData(key, railCode, lineCd, stinCode)

}