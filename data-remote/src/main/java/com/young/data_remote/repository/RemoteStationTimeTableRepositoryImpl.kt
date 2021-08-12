package com.young.data_remote.repository

import com.young.data_remote.api.TrailPorTalService
import com.young.data_remote.datasource.RemoteStationTimeDataSource
import com.young.data_remote.mapper.RemoteToDomainMapper.RemoteToDomain
import com.young.data_remote.model.Header
import com.young.data_remote.model.RemoteStationTimeTable
import com.young.data_remote.model.TimeTableBody
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainTrailTimeTable
import com.young.domain.repository.remote.RemoteStationTimeTableRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteStationTimeTableRepositoryImpl @Inject constructor(
    private val service: TrailPorTalService
) : RemoteStationTimeDataSource {
    override suspend fun getRemoteStationTimeTable(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String
    ): Flow<RemoteStationTimeTable> = flowOf(
        service.getStationTimetables(
            key = key,
            format = "json",
            trailCode = railCode,
            toDayCode = dayCd,
            lineCode = lineCode,
            stationCode = stationCode
        )
    )
}