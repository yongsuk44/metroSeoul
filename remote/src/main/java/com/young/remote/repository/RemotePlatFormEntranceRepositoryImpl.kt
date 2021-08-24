package com.young.remote.repository

import com.young.remote.api.TrailPorTalService
import com.young.cache.datasource.remote.RemotePlatformEntranceDataSource
import com.young.remote.model.RemotePlatformEntrance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemotePlatFormEntranceRepositoryImpl @Inject constructor(
    val dao: TrailPorTalService
) : RemotePlatformEntranceDataSource {
    override suspend fun getPlatformEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ): Flow<RemotePlatformEntrance> =
        flowOf(
            dao.getPlatformEntranceData(key, "json", railCode, lineCd, stinCode)
        )
            .flowOn(Dispatchers.IO)
}