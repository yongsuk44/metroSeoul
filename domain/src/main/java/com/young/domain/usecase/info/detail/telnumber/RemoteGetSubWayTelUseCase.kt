package com.young.domain.usecase.info.detail.telnumber

import com.young.domain.model.DomainCustomerService
import com.young.domain.model.DomainSubWayTel
import com.young.domain.repository.informationdetail.remote.RemoteSubWayTelRepository
import com.young.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


interface RemoteGetSubWayTelBaseUseCase {
    suspend fun getSeoulApiTelData(key : String) : Flow<List<DomainSubWayTel>>
    suspend fun getPublicApiTelData(apiPath : String , key: String) : Flow<DomainCustomerService>
}

class RemoteGetSubWayTelUseCase @Inject constructor(
    private val remote : RemoteSubWayTelRepository
) : RemoteGetSubWayTelBaseUseCase {
    override suspend fun getPublicApiTelData(
        apiPath: String,
        key: String
    ): Flow<DomainCustomerService> =
        remote.getStationTelData(apiPath , key)


    override suspend fun getSeoulApiTelData(key: String): Flow<List<DomainSubWayTel>> =
        remote.getSubWayTelData(key)

}