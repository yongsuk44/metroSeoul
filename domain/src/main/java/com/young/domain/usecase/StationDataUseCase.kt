package com.young.domain.usecase

import com.young.domain.model.AllRouteInformation
import com.young.domain.model.DomainStationTelNumber
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.repository.location.CacheFullRouteInformationRepository
import com.young.domain.repository.remote.RemoteStationDataRepository
import com.young.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias StationDataBaseUseCase = BaseUseCase<List<String>, Flow<List<AllRouteInformation>>>

interface RemoteStationTelBaseUseCase {
    suspend fun getStationTelData(
        publicDataKey: String,
        stationCode: String
    ): Flow<DomainStationTelNumber>

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

class StationDataUseCase @Inject constructor(
    val cache: CacheFullRouteInformationRepository,
    private val remote: RemoteStationDataRepository
) : StationDataBaseUseCase, RemoteStationTelBaseUseCase {
    override suspend fun invoke(param: List<String>): Flow<List<AllRouteInformation>> =
        cache.getStationData(param)

    override suspend fun getStationTelData(
        publicDataKey: String,
        stationCode: String
    ): Flow<DomainStationTelNumber> =
        remote.getStationTelData(publicDataKey, stationCode)

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