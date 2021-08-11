package com.young.domain.repository.remote

import com.young.domain.model.DomainAllStationCodes
import com.young.domain.model.DomainStationTelNumber
import kotlinx.coroutines.flow.Flow

interface RemoteStationTelRepository {
    suspend fun getStationTelData(publicDataKey: String , stationCode: String) : Flow<DomainStationTelNumber>
    suspend fun getAllStationCode(seoulKey: String) : Flow<DomainAllStationCodes>
}