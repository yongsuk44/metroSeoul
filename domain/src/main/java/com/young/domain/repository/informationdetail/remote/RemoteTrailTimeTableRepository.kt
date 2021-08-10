package com.young.domain.repository.informationdetail.remote

import com.young.domain.model.DomainTrailTimeTable
import kotlinx.coroutines.flow.Flow

interface RemoteTrailTimeTableRepository {
    suspend fun getTrailTimetables(
        key :String ,
        railCode : String ,
        dayCd : String ,
        lineCode : String ,
        stationCode : String
    ) : Flow<DomainTrailTimeTable>
}