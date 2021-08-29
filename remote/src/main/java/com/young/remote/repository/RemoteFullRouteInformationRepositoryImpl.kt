package com.young.remote.repository

import com.young.data.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.data.model.DataConvenienceInformation
import com.young.data.model.DataFullRouteInformation
import com.young.data.model.DataPlatformEntrance
import com.young.data.model.DataRow
import com.young.remote.api.SeoulApiService
import com.young.remote.api.TrailPorTalService
import com.young.remote.mapper.RemoteToDataMapper.RemoteToData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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
    ): Flow<DataPlatformEntrance> = flowOf(
        service.getPlatformEntranceData(key , "json" , railCode , lineCd , stinCode).RemoteToData()
    )

    override suspend fun getStationRouteInformation(
        key: String
    ): Flow<DataFullRouteInformation> = flowOf(
        service.getStationRouteInformation(key, "json", "01" , null).RemoteToData()
    )

    override suspend fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    ): Flow<DataConvenienceInformation> = flowOf(
        service.getConvenienceInformation(key , "json" , lineCode, trailCode, stationCode).RemoteToData()
    )

    override suspend fun getAllStationCode(seoulKey: String): Flow<List<DataRow>> = flowOf(
        seoulApiService.getStationNameToAllStationCodes(seoulKey).RemoteToData()
    )
}