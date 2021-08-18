package com.young.domain.usecase.remote

import com.young.domain.model.DomainStationTimeTable
import com.young.domain.repository.remote.RemoteStationTimeTableRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RemoteTimeTableBaseUseCase {
    suspend fun getStationTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String,
        updown: String
    ): Flow<DomainStationTimeTable>

    suspend fun getSeoulStationTimeTable(
        key: String,
        updown: String,
        dayCd: String,
        stationCode: String
    ): Flow<DomainStationTimeTable>
}

class RemoteTimeTableUseCase @Inject constructor(
    private val remote: RemoteStationTimeTableRepository
) : RemoteTimeTableBaseUseCase {
    override suspend fun getStationTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String,
        updown: String
    ): Flow<DomainStationTimeTable> =
        remote.getStationTimetables(key, railCode, dayCd, lineCode, stationCode, updown)

    override suspend fun getSeoulStationTimeTable(
        key: String,
        updown: String,
        dayCd: String,
        stationCode: String
    ): Flow<DomainStationTimeTable> =
        remote.getSeoulStationTimeTable(key, updown, dayCd, stationCode)
}