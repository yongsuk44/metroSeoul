package com.young.domain.repository.remote

import com.young.domain.model.*
import kotlinx.coroutines.flow.Flow

interface RemoteFullRouteInformationRepository {
    suspend fun getStationRouteInformation(key : String) : Flow<List<DomainFullRouteInformationBody>>
    suspend fun getConvenienceInformation(key: String, lineCode: String, trailCode: String, stationCode: String) : Flow<DomainConvenienceInformation>
    suspend fun getAllStationCode(seoulKey: String) : Flow<List<DomainRow>>
    suspend fun getPlatformEntranceData(key : String , railCode : String ,lineCd : String, stinCode : String) : Flow<DomainPlatformEntrance>
}