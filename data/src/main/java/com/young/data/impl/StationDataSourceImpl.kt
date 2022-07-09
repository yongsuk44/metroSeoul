package com.young.data.impl

import com.young.data.mapper.DataToDomainMapper.DataToDomain
import com.young.domain.model.DomainStationBody
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.repository.StationDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StationDataSourceImpl @Inject constructor(
    private val remote: com.young.data.datasource.remote.RemoteStationDataSource
) : StationDataRepository {
    override suspend fun getStationTelData(
        publicDataKey: String,
        stationCode: String
    ): Flow<List<DomainStationBody>?> =
        remote.getStationTelData(publicDataKey, stationCode).map {
            it?.map { it.DataToDomain() }
        }


    override suspend fun getStationTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String,
        updown: String
    ): Flow<DomainStationTimeTable?> =
        remote.getDataStationTimeTable(key, railCode, dayCd, lineCode, stationCode, updown).map {
            it.DataToDomain(updown)
        }

    override suspend fun getSeoulStationTimeTable(
        key: String,
        updown: String,
        dayCd: String,
        stationCode: String
    ): Flow<DomainStationTimeTable?> =
        remote.getDataSeoulStationTimeTable(key, updown, dayCd, stationCode).map {
            it.DataToDomain()
        }


}