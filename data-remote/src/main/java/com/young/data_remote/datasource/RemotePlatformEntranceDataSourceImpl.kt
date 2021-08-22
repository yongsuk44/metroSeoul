package com.young.data_remote.datasource

import com.young.data_remote.mapper.RemoteToDomainMapper.RemoteToDomain
import com.young.domain.model.DomainPlatformEntrance
import com.young.domain.repository.remote.RemotePlatformEntranceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemotePlatformEntranceDataSourceImpl @Inject constructor(
    private val datasource: RemotePlatformEntranceDataSource
) : RemotePlatformEntranceRepository {
    override suspend fun getPlatformEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ): Flow<DomainPlatformEntrance> = flow {
        datasource.getPlatformEntranceData(key, railCode, lineCd, stinCode)
            .map {
                it.RemoteToDomain()
            }
    }
}