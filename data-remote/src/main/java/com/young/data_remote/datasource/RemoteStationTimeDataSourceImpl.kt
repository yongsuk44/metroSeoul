package com.young.data_remote.datasource

import com.young.data_remote.mapper.RemoteToDomainMapper.RemoteToDomain
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.repository.remote.RemoteStationTimeTableRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteStationTimeDataSourceImpl @Inject constructor(
    private val dataSource: RemoteStationTimeDataSource
) : RemoteStationTimeTableRepository {
    override suspend fun getStationTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String,
        updown: String
    ): Flow<DomainStationTimeTable> =
        dataSource.getRemoteStationTimeTable(key, railCode, dayCd, lineCode, stationCode ,updown).map {
            it.RemoteToDomain()
        }

    override suspend fun getSeoulStationTimeTable(
        key: String,
        updown: String,
        dayCd: String,
        stationCode: String
    ): Flow<DomainStationTimeTable> =
        dataSource.getRemoteSeoulStationTimeTable(key, updown, dayCd, stationCode).map {
            it.RemoteToDomain()
        }


}