package com.young.domain.repository.informationdetail.local

import com.young.domain.model.DomainStationTel
import kotlinx.coroutines.flow.Flow

interface LocalStationTelRepository {
    suspend fun insert(items : List<DomainStationTel>)
    suspend fun getStationTelData(stinCd : String) : Flow<DomainStationTel>
}
