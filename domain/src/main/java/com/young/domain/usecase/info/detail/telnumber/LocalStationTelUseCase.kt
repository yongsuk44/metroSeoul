package com.young.domain.usecase.info.detail.telnumber

import com.young.domain.model.DomainStationTel
import com.young.domain.repository.informationdetail.local.LocalStationTelRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalStationTelBaseUseCase {
    suspend fun insert(items : List<DomainStationTel>)
    suspend fun getStationTelData(stinCd : String) : Flow<DomainStationTel>
}

class LocalStationTelUseCase @Inject constructor(
    val local : LocalStationTelRepository
) : LocalStationTelBaseUseCase {
    override suspend fun insert(items: List<DomainStationTel>) {
        local.insert(items)
    }

    override suspend fun getStationTelData(stinCd : String): Flow<DomainStationTel> =
        local.getStationTelData(stinCd)

}