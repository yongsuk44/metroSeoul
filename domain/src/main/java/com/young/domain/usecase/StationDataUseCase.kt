package com.young.domain.usecase

import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.model.DomainStationBody
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.repository.FullRouteInformationRepository
import com.young.domain.repository.StationDataRepository
import com.young.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias StationDataBaseUseCase = BaseUseCase<List<String>, Flow<List<DomainFullRouteInformationBody>>>

interface RemoteStationDataBaseUseCase {
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
    private val fullRouteInformationRepository: FullRouteInformationRepository,
    private val remote: StationDataRepository
) : StationDataBaseUseCase, RemoteStationDataBaseUseCase {
    override suspend fun invoke(param: List<String>): Flow<List<DomainFullRouteInformationBody>> =
        fullRouteInformationRepository.readStationData(param)

    override suspend fun getStationTelData(
        publicDataKey: String,
        stationCode: String
    ): Flow<List<DomainStationBody>?> =
        remote.getStationTelData(publicDataKey, stationCode)

    override suspend fun getStationTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String,
        updown: String
    ): Flow<DomainStationTimeTable?> =
        remote.getStationTimetables(key, railCode, dayCd, lineCode, stationCode, updown)

    override suspend fun getSeoulStationTimeTable(
        key: String,
        updown: String,
        dayCd: String,
        stationCode: String
    ): Flow<DomainStationTimeTable?> =
        remote.getSeoulStationTimeTable(key, updown, dayCd, stationCode)


}