package com.young.remote.repository

import com.young.cache.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.cache.model.DataConvenienceInformation
import com.young.cache.model.DataFullRouteInformation
import com.young.cache.model.DataPlatformEntrance
import com.young.cache.model.DataRow
import com.young.remote.api.SeoulApiService
import com.young.remote.api.TrailPorTalService
import com.young.remote.mapper.RemoteToDataMapper.RemoteToData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteFullRouteInformationRepositoryImpl @Inject constructor(
    private val seoulApiService: SeoulApiService,
    private val service: TrailPorTalService
) : RemoteFullRouteInformationDataSource {
    override suspend fun getPlatformEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ): Flow<DataPlatformEntrance> = flow {
        emit(service.getPlatformEntranceData(key , "json" , railCode , lineCd , stinCode).RemoteToData())
    }

    override suspend fun getFullRouteInformation(
        key: String
    ): Flow<DataFullRouteInformation> = flow {
        emit(service.getFullRouteInformation(key, "json", "01").RemoteToData())
    }


    override suspend fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    ): Flow<DataConvenienceInformation> = flow {
        service.getConvenienceInformation(key , "json" , lineCode, trailCode, stationCode).RemoteToData()
    }

    override suspend fun getAllStationCode(seoulKey: String): Flow<List<DataRow>> = flow {
        emit(seoulApiService.getStationNameToAllStationCodes(seoulKey).RemoteToData())
    }
}