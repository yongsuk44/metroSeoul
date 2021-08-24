package com.young.cache.impl.remote

import com.young.cache.datasource.remote.RemoteStationDataSource
import com.young.cache.mapper.DataToDomainMapper.DataToDomain
import com.young.domain.model.DomainStationTelNumber
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.repository.remote.RemoteStationDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteStationDataSourceImpl @Inject constructor(
    private val dataSource: RemoteStationDataSource
) : RemoteStationDataRepository {
    override suspend fun getStationTelData(
        publicDataKey: String,
        stationCode: String
    ): Flow<DomainStationTelNumber> =
        dataSource.getStationTelData(publicDataKey, stationCode).map {
            it.DataToDomain()
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