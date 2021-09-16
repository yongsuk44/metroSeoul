package com.young.domain.fake

import com.young.domain.model.DomainConvenienceInformation
import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.model.DomainPlatformEntrance
import com.young.domain.model.DomainRow
import com.young.domain.repository.remote.RemoteFullRouteInformationRepository
import kotlinx.coroutines.flow.Flow

interface FakeRemoteFullRouteInformationRepository : RemoteFullRouteInformationRepository {
    override suspend fun getStationRouteInformation(key: String): Flow<List<DomainFullRouteInformationBody>> {
        TODO("Not yet implemented")
    }

    override suspend fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    ): Flow<DomainConvenienceInformation> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllStationCode(seoulKey: String): Flow<List<DomainRow>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPlatformEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ): Flow<DomainPlatformEntrance> {
        TODO("Not yet implemented")
    }
}