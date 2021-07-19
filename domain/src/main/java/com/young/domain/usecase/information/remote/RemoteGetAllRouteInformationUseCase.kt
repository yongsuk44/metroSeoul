package com.young.domain.usecase.information.remote

import com.young.domain.model.DomainAllRouteInformation
import com.young.domain.repository.information.RemoteFullRouteInformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RemoteFullRouteInformationUseCase {
    suspend fun getFullRouteInformation(key : String , lineCode: String) : Flow<DomainAllRouteInformation>
}

class RemoteGetFullRouteInformationUseCase @Inject constructor(
    private val remote : RemoteFullRouteInformationRepository
) : RemoteFullRouteInformationUseCase {

    override suspend fun getFullRouteInformation(
        key: String,
        lineCode: String
    ): Flow<DomainAllRouteInformation> =
        remote.getFullRouteInformation(key, lineCode)

}