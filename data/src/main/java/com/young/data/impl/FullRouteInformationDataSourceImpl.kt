package com.young.data.impl

import com.young.data.datasource.cache.CacheFullRouteInformationDataSource
import com.young.data.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.data.mapper.DataToDomainMapper.DataToDomain
import com.young.data.mapper.DomainToDataMapper.DomainToData
import com.young.domain.model.*
import com.young.domain.repository.FullRouteInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FullRouteInformationDataSourceImpl constructor(
    private val cache: CacheFullRouteInformationDataSource,
    private val remote: RemoteFullRouteInformationDataSource
) : FullRouteInformationRepository {
    override suspend fun insert(param: List<DomainFullRouteInformationBody>): Flow<List<Long>> =
        cache.insert(param.map { it.DomainToData() })

    override suspend fun insertLineCodeAndTrailCode(param: List<DomainTrailCodeAndLineCode>): Flow<List<Long>> =
        cache.insertLineCodeAndTrailCode(param.map { it.DomainToData() })

    override suspend fun readDataSize(): Flow<Int> =
        cache.readDataSize()

    override suspend fun readTrailCodeAllData(): Flow<List<DomainTrailCodeAndLineCode>> =
        cache.readTrailCodeAllData().map { it.map { it.DataToDomain() } }

    override suspend fun readStationData(stinCode: List<String>): Flow<List<DomainFullRouteInformationBody>> =
        cache.readStationData(stinCode).map { it.map { it.DataToDomain() } }

    override suspend fun readStationNameToFullRouteInformationData(name: String): Flow<DomainFullRouteInformationBody> =
        cache.readStationNameToFullRouteInformationData(name).map { it.DataToDomain() }

    override suspend fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    ): Flow<DomainConvenienceInformation> =
        remote.getConvenienceInformation(key, lineCode, trailCode, stationCode).map { it.DataToDomain() }

    override suspend fun getAllStationCode(seoulKey: String): Flow<List<DomainRow>> =
        remote.getAllStationCode(seoulKey).map { it.map { it.DataToDomain() } }

    override suspend fun getStationEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ): Flow<DomainStationEntrance> =
        remote.getStationEntranceData(key, railCode, lineCd, stinCode).map { it.DataToDomain() }

    override suspend fun findStationRouteInformation(key: String?): Flow<List<DomainFullRouteInformationBody>> =
        key?.let {
            remote.findStationRouteInformation(key)
                .map { it.body }
                .map { it.map { it.DataToDomain() } }
        } ?: kotlin.run {
            cache.findStationRouteInformation(null)
                .map { it.map { it.DataToDomain() } }
        }

}