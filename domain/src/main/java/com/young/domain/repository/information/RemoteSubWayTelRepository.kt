package com.young.domain.repository.information

import com.young.domain.model.DomainSubWayTel
import kotlinx.coroutines.flow.Flow

interface RemoteSubWayTelRepository {
    suspend fun getSubWayTelData(key : String) : Flow<List<DomainSubWayTel>>
}