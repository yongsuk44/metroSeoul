package com.young.data.impl.remote

import com.young.data.mapper.DataToDomainMapper.DataToDomain
import com.young.domain.model.DomainStationBody
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.repository.remote.RemoteStationDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteStationDataSourceImpl @Inject constructor(
    private val dataSource: com.young.data.datasource.remote.RemoteStationDataSource
) : RemoteStationDataRepository {
    override suspend fun getStationTelData(
        publicDataKey: String,
        stationCode: String
    ): Flow<List<DomainStationBody>?> =
        dataSource.getStationTelData(publicDataKey, stationCode).map {
            it?.map { it.DataToDomain() }
        }


    override suspend fun getStationTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String,
        updown: String
    ): Flow<DomainStationTimeTable> =
        dataSource.getDataStationTimeTable(key, railCode, dayCd, lineCode, stationCode ,updown).map {
            it.DataToDomain(updown)
        }

    override suspend fun getSeoulStationTimeTable(
        key: String,
        updown: String,
        dayCd: String,
        stationCode: String
    ): Flow<DomainStationTimeTable> =
        dataSource.getDataSeoulStationTimeTable(key, updown, dayCd, stationCode).map {
            it.DataToDomain()
        }


}