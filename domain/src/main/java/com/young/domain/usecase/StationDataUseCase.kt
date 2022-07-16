package com.young.domain.usecase

import com.young.domain.model.DomainStationBody
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.repository.StationDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface StationDataBaseUseCase {
    suspend fun getStationTelData(
        publicDataKey: String,
        stationCode: String
    ): Flow<List<DomainStationBody>?>

    suspend fun getStationTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String,
        updown: String
    ): Flow<DomainStationTimeTable?>

    suspend fun getSeoulStationTimeTable(
        key: String,
        updown: String,
        dayCd: String,
        stationCode: String
    ): Flow<DomainStationTimeTable?>
}

class StationDataUseCase @Inject constructor(
    private val repository: StationDataRepository
) : StationDataBaseUseCase {

    override suspend fun getStationTelData(
        publicDataKey: String,
        stationCode: String
    ): Flow<List<DomainStationBody>?> =
        repository.getStationTelData(publicDataKey, stationCode)

    override suspend fun getStationTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String,
        updown: String
    ): Flow<DomainStationTimeTable?> =
        repository.getStationTimetables(key, railCode, dayCd, lineCode, stationCode, updown)

    override suspend fun getSeoulStationTimeTable(
        key: String,
        updown: String,
        dayCd: String,
        stationCode: String
    ): Flow<DomainStationTimeTable?> =
        repository.getSeoulStationTimeTable(key, updown, dayCd, stationCode)


}