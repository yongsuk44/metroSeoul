package com.young.domain.usecase.remote

import com.young.domain.model.DomainTrailTimeTable
import com.young.domain.repository.remote.RemoteStationTimeTableRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RemoteTimeTableUseCase {
    suspend fun getStationTimetables(
        key :String ,
        railCode : String ,
        dayCd : String ,
        lineCode : String ,
        stationCode : String
    ) : Flow<DomainTrailTimeTable>
}

class RemoteGetTimeTableUseCaseImpl @Inject constructor(
    private val remote : RemoteStationTimeTableRepository
) : RemoteTimeTableUseCase {
    override suspend fun getStationTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String
    ): Flow<DomainTrailTimeTable> =
        remote.getStationTimetables(key, railCode, dayCd, lineCode, stationCode)

}