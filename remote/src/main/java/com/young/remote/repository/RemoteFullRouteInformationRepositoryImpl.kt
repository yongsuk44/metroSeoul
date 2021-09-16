package com.young.remote.repository

import com.young.data.model.DataConvenienceInformation
import com.young.data.model.DataFullRouteInformation
import com.young.data.model.DataPlatformEntrance
import com.young.data.model.DataRow
import com.young.remote.api.SeoulApiService
import com.young.remote.api.TrailPorTalService
import com.young.remote.mapper.RemoteToDataMapper.RemoteToData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteFullRouteInformationRepositoryImpl @Inject constructor(
    private val seoulApiService: SeoulApiService,
    private val service: TrailPorTalService
) : com.young.data.datasource.remote.RemoteFullRouteInformationDataSource {
    override suspend fun getPlatformEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ): Flow<DataPlatformEntrance> = flow {
        emit(service.getPlatformEntranceData(key, "json", railCode, lineCd, stinCode).RemoteToData())
    }.flowOn(Dispatchers.IO)

    override suspend fun getStationRouteInformation(
        key: String
    ): Flow<DataFullRouteInformation> = flow {
        emit(service.getStationRouteInformation(key, "json", "01", null).RemoteToData())
    }.flowOn(Dispatchers.IO)

    override suspend fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    ): Flow<DataConvenienceInformation> = flow {
        emit(service.getConvenienceInformation(key, "json", lineCode, trailCode, stationCode).RemoteToData())
    }.flowOn(Dispatchers.IO)

    override suspend fun getAllStationCode(seoulKey: String): Flow<List<DataRow>> = flow {
        emit(seoulApiService.getStationNameToAllStationCodes(seoulKey).RemoteToData())
    }.flowOn(Dispatchers.IO)
}