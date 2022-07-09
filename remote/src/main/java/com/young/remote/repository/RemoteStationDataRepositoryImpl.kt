package com.young.remote.repository

import com.young.data.model.DataStationBody
import com.young.data.model.DataStationSeoulTimeTable
import com.young.data.model.DataStationTimeTable
import com.young.remote.api.PublicDataOpenApiService
import com.young.remote.api.SeoulApiService
import com.young.remote.api.TrailPorTalService
import com.young.remote.mapper.RemoteToDataMapper.RemoteToData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteStationDataRepositoryImpl @Inject constructor(
    private val publicDataPortalApiService: PublicDataOpenApiService,
    private val service: TrailPorTalService,
    private val seoulApi: SeoulApiService,
    private val dispatcher: CoroutineDispatcher
) : com.young.data.datasource.remote.RemoteStationDataSource {
    override suspend fun getStationTelData(
        publicDataKey: String,
        stationCode: String
    ): Flow<List<DataStationBody>?> = flowOf(
        publicDataPortalApiService.getAllRouteInformationData(
            publicDataKey,
            1,
            1000,
            "json",
            stationCode
        ).RemoteToData()
    ).flowOn(dispatcher)

    override suspend fun getDataStationTimeTable(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String,
        updown: String
    ): Flow<DataStationTimeTable> = flowOf(
        service.getStationTimetables(
            key = key,
            format = "json",
            trailCode = railCode,
            toDayCode = dayCd,
            lineCode = lineCode,
            stationCode = stationCode
        ).RemoteToData()
    ).flowOn(dispatcher)

    override suspend fun getDataSeoulStationTimeTable(
        key: String,
        updown: String,
        dayCd: String,
        stationCode: String
    ): Flow<DataStationSeoulTimeTable> = flowOf(
        seoulApi.getStationTimeTable(
            key = key,
            code = stationCode,
            dayCode = dayCd,
            updown = updown
        ).RemoteToData()
    ).flowOn(dispatcher)
}