package com.young.cache.impl.remote

import com.young.cache.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.cache.mapper.DataToDomainMapper.DataToDomain
import com.young.domain.model.*
import com.young.domain.repository.remote.RemoteFullRouteInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteFullRouteInformationDataSourceImpl @Inject constructor(
    private val datasource: RemoteFullRouteInformationDataSource
) : RemoteFullRouteInformationRepository {
    override suspend fun getFullRouteInformation(key: String): Flow<List<DomainFullRouteInformationBody>> =
        datasource.getFullRouteInformation(key).map {
            it.body.map {
                it.DataToDomain()
            }
        }

    override suspend fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    ): Flow<DomainConvenienceInformation> =
        datasource.getConvenienceInformation(key, lineCode, trailCode, stationCode).map {
            it.DataToDomain()
        }

    override suspend fun getAllStationCode(seoulKey: String): Flow<List<DomainFullRouteInformationBody>> =
        datasource.getAllStationCode(seoulKey).map {
            it.body.map { it.DataToDomain() }
        }

    override suspend fun getPlatformEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ): Flow<DomainPlatformEntrance> = flow {
        datasource.getPlatformEntranceData(key, railCode, lineCd, stinCode)
            .map {
                it.body.map { it.DataToDomain() }
            }
    }
}