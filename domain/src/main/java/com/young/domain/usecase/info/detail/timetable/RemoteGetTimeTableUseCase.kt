package com.young.domain.usecase.info.detail.timetable

import com.young.domain.model.DomainTrailTimeTable
import com.young.domain.repository.informationdetail.remote.RemoteTrailTimeTableRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RemoteTimeTableUseCase {
    suspend fun getTrailTimetables(
        key :String ,
        railCode : String ,
        dayCd : String ,
        lineCode : String ,
        stationCode : String
    ) : Flow<DomainTrailTimeTable>
}

class RemoteGetTimeTableUseCaseImpl @Inject constructor(
    private val remote : RemoteTrailTimeTableRepository
) : RemoteTimeTableUseCase {
    override suspend fun getTrailTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String
    ): Flow<DomainTrailTimeTable> =
        remote.getTrailTimetables(key, railCode, dayCd, lineCode, stationCode)

}