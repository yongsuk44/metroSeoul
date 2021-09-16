package com.young.domain.fake

import com.young.domain.factory.ModelFactory
import com.young.domain.factory.ModelFactory.generatePublicDomainStationTimeTable
import com.young.domain.factory.ModelFactory.generateSeoulDomainStationTimeTable
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.repository.remote.RemoteStationDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface FakeStationTimeTableRepository : RemoteStationDataRepository {
    override suspend fun getStationTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String,
        updown: String
    ): Flow<DomainStationTimeTable?> = flowOf(generatePublicDomainStationTimeTable())

    override suspend fun getSeoulStationTimeTable(
        key: String,
        updown: String,
        dayCd: String,
        stationCode: String
    ): Flow<DomainStationTimeTable> = flowOf(generateSeoulDomainStationTimeTable())
}