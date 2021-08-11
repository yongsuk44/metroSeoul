package com.young.domain.usecase.remote

import com.young.domain.model.DomainAllStationCodes
import com.young.domain.model.DomainStationTelNumber
import com.young.domain.repository.remote.RemoteStationTelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface RemoteStationTelBaseUseCase {
    suspend fun getStationTelData(publicDataKey: String , stationCode: String) : Flow<DomainStationTelNumber>
    suspend fun getAllStationCode(seoulKey: String) : Flow<DomainAllStationCodes>
}

class RemoteStationTelUseCase @Inject constructor(
    private val remote : RemoteStationTelRepository
) : RemoteStationTelBaseUseCase {

    override suspend fun getStationTelData(publicDataKey: String , stationCode: String) : Flow<DomainStationTelNumber> =
        remote.getStationTelData(publicDataKey, stationCode)

    override suspend fun getAllStationCode(seoulKey: String): Flow<DomainAllStationCodes> =
        remote.getAllStationCode(seoulKey)

}