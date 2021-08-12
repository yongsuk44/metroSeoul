package com.young.data_remote.datasource

import com.young.data_remote.mapper.RemoteToDomainMapper.RemoteToDomain
import com.young.data_remote.model.RemoteStationTimeTable
import com.young.domain.model.DomainTrailTimeTable
import com.young.domain.repository.remote.RemoteStationTimeTableRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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
        stationCode: String
    ): Flow<DomainTrailTimeTable> =
        dataSource.getRemoteStationTimeTable(key, railCode, dayCd, lineCode, stationCode).map {
            it.RemoteToDomain()
        }

}