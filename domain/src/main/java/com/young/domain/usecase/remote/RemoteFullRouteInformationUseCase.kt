package com.young.domain.usecase.remote

import com.young.domain.model.DomainAllRouteInformation
import com.young.domain.model.DomainConvenienceInformation
import com.young.domain.repository.remote.RemoteFullRouteInformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RemoteFullRouteInformationUseCase {
    suspend fun getFullRouteInformation(key : String) : Flow<DomainAllRouteInformation>
    suspend fun getConvenienceInformation(key: String, lineCode: String, trailCode: String, stationCode: String) : Flow<DomainConvenienceInformation>
}

class RemoteGetFullRouteInformationUseCase @Inject constructor(
    private val remote : RemoteFullRouteInformationRepository
) : RemoteFullRouteInformationUseCase {

    override suspend fun getFullRouteInformation(
        key: String
    ): Flow<DomainAllRouteInformation> =
        remote.getFullRouteInformation(key)

    override suspend fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    ): Flow<DomainConvenienceInformation> =
        remote.getConvenienceInformation(key, lineCode, trailCode, stationCode)

}