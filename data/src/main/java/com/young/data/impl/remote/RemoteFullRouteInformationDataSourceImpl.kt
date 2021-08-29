package com.young.data.impl.remote

import com.young.data.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.data.mapper.DataToDomainMapper.DataToDomain
import com.young.domain.model.DomainConvenienceInformation
import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.model.DomainPlatformEntrance
import com.young.domain.model.DomainRow
import com.young.domain.repository.remote.RemoteFullRouteInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteFullRouteInformationDataSourceImpl @Inject constructor(
    private val datasource: RemoteFullRouteInformationDataSource
) : RemoteFullRouteInformationRepository {
    override suspend fun getStationRouteInformation(key: String): Flow<List<DomainFullRouteInformationBody>> =
        datasource.getStationRouteInformation(key).map {
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

    override suspend fun getAllStationCode(seoulKey: String): Flow<List<DomainRow>> =
        datasource.getAllStationCode(seoulKey).map {
            it.map { it.DataToDomain() }
        }

    override suspend fun getPlatformEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ): Flow<DomainPlatformEntrance> =
        datasource.getPlatformEntranceData(key, railCode, lineCd, stinCode)
            .map { it.DataToDomain() }

}