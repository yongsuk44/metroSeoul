package com.young.data_remote.repository

import com.young.data_remote.api.PublicDataOpenApiService
import com.young.data_remote.api.SeoulApiService
import com.young.data_remote.mapper.RemoteToDomainMapper.RemoteToDomain
import com.young.domain.model.DomainAllStationCodes
import com.young.domain.model.DomainStationTelNumber
import com.young.domain.repository.remote.RemoteStationTelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteStationTelRepositoryImpl @Inject constructor(
    private val service: SeoulApiService,
    private val publicDataPortalApiService: PublicDataOpenApiService
) : RemoteStationTelRepository {
    override suspend fun getAllStationCode(seoulKey: String): Flow<DomainAllStationCodes> = flow {
        emit(
            service.getStationNameToAllStationCodes(seoulKey)
            .RemoteToDomain()
        )
    }

    override suspend fun getStationTelData(publicDataKey: String , stationCode: String) : Flow<DomainStationTelNumber> = flow {
        emit(
            publicDataPortalApiService.getAllRouteInformationData(publicDataKey , 1 , 10 , "json" , stationCode)
            .RemoteToDomain()
        )
    }

}