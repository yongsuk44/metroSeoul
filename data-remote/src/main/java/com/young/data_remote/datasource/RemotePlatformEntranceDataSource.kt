package com.young.data_remote.datasource

import com.young.data_remote.model.RemotePlatformEntrance
import com.young.domain.model.DomainPlatformEntrance
import kotlinx.coroutines.flow.Flow

interface RemotePlatformEntranceDataSource {
    suspend fun getPlatformEntranceData(key : String , railCode : String ,lineCd : String, stinCode : String) : Flow<RemotePlatformEntrance>

}