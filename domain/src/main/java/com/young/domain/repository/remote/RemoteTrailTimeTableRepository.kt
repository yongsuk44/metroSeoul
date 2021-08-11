package com.young.domain.repository.remote

import com.young.domain.model.DomainTrailTimeTable
import kotlinx.coroutines.flow.Flow

interface RemoteStationTimeTableRepository {
    suspend fun getStationTimetables(
        key :String ,
        railCode : String ,
        dayCd : String ,
        lineCode : String ,
        stationCode : String
    ) : Flow<DomainTrailTimeTable>
}