package com.young.domain.repository.remote

import com.young.domain.model.DomainConvenienceInformation
import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.model.DomainStationEntrance
import com.young.domain.model.DomainRow
import kotlinx.coroutines.flow.Flow

interface RemoteFullRouteInformationRepository {
    suspend fun getStationRouteInformation(key : String) : Flow<List<DomainFullRouteInformationBody>>
    suspend fun getConvenienceInformation(key: String, lineCode: String, trailCode: String, stationCode: String) : Flow<DomainConvenienceInformation>
    suspend fun getAllStationCode(seoulKey: String) : Flow<List<DomainRow>>
    suspend fun getStationEntranceData(key : String , railCode : String ,lineCd : String, stinCode : String) : Flow<DomainStationEntrance>
}