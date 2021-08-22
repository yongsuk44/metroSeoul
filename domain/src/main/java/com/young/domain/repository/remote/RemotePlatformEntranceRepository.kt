package com.young.domain.repository.remote

import com.young.domain.model.DomainPlatformEntrance
import kotlinx.coroutines.flow.Flow

interface RemotePlatformEntranceRepository {
    suspend fun getPlatformEntranceData(key : String , railCode : String ,lineCd : String, stinCode : String) : Flow<DomainPlatformEntrance>
}