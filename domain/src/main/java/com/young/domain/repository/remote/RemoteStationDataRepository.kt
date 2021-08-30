package com.young.domain.repository.remote

import com.young.domain.model.DomainStationBody
import com.young.domain.model.DomainStationTimeTable
import kotlinx.coroutines.flow.Flow

interface RemoteStationDataRepository {
    suspend fun getStationTelData(publicDataKey: String , stationCode: String) : Flow<List<DomainStationBody>?>
    suspend fun getStationTimetables(
        key :String ,
        railCode : String ,
        dayCd : String ,
        lineCode : String ,
        stationCode : String,
        updown: String
    ) : Flow<DomainStationTimeTable>

    suspend fun getSeoulStationTimeTable(
        key: String,
        updown : String,
        dayCd: String,
        stationCode: String
    ): Flow<DomainStationTimeTable>
}