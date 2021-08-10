package com.young.domain.repository.informationdetail.remote

import com.young.domain.model.DomainCustomerService
import com.young.domain.model.DomainSubWayTel
import kotlinx.coroutines.flow.Flow

interface RemoteSubWayTelRepository {
    suspend fun getSubWayTelData(key : String) : Flow<List<DomainSubWayTel>>
    suspend fun getStationTelData(apiService : String , key : String) : Flow<DomainCustomerService>
}