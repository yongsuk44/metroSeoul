package com.young.domain.timetable

import com.young.domain.factory.timetableFactory
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.repository.remote.RemoteStationTimeTableRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface FakeStationTimeTableRepository : RemoteStationTimeTableRepository {
    override suspend fun getStationTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String,
        updown: String
    ): Flow<DomainStationTimeTable?> = flowOf(timetableFactory.generatePublicDomainStationTimeTable())

    override suspend fun getSeoulStationTimeTable(
        key: String,
        updown: String,
        dayCd: String,
        stationCode: String
    ): Flow<DomainStationTimeTable> = flowOf(timetableFactory.generateSeoulDomainStationTimeTable())
}