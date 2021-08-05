package com.young.domain.repository.informationdetail

import com.young.domain.model.DomainPlatformEntrance
import kotlinx.coroutines.flow.Flow

interface RemoteDetailInformationRepository {
    suspend fun getPlatformAtTheEntranceData(key : String , railCode : String ,lineCd : String, stinCode : String) : Flow<DomainPlatformEntrance>
}