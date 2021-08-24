package com.young.remote.repository

import com.young.remote.api.SeoulApiService
import com.young.remote.api.TrailPorTalService
import com.young.cache.datasource.remote.RemoteStationTimeDataSource
import com.young.remote.model.RemoteStationSeoulTimeTable
import com.young.remote.model.RemoteStationTimeTable
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RemoteStationTimeTableRepositoryImpl @Inject constructor(
    private val service: TrailPorTalService ,
    private val seoulApi : SeoulApiService
) : RemoteStationTimeDataSource {
    override suspend fun getRemoteStationTimeTable(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String,
        updown: String
    ): Flow<RemoteStationTimeTable> = flowOf(
        service.getStationTimetables(
            key = key,
            format = "json",
            trailCode = railCode,
            toDayCode = dayCd,
            lineCode = lineCode,
            stationCode = stationCode
        )
    ).flatMapConcat {
        flow {
            emit(
                if (it.body == null) {
                    service.getStationTimetables(key = key,
                        format = "json",
                        trailCode = railCode,
                        toDayCode = "9",
                        lineCode = lineCode,
                        stationCode = stationCode
                    )
                } else {
                    it
                }
            )
        }
    }

    override suspend fun getRemoteSeoulStationTimeTable(
        key: String,
        updown : String,
        dayCd: String,
        stationCode: String
    ): Flow<RemoteStationSeoulTimeTable> = flowOf(
        seoulApi.getStationTimeTable(
            key = key ,
            code = stationCode,
            dayCode = dayCd ,
            updown = updown
        )
    )
}