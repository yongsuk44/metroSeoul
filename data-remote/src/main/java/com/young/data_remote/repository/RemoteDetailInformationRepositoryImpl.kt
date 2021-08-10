package com.young.data_remote.repository

import com.young.data_remote.api.TrailPorTalService
import com.young.data_remote.mapper.RemoteToDomainMapper.RemoteToDomain
import com.young.domain.model.DomainPlatformEntrance
import com.young.domain.repository.informationdetail.remote.RemoteDetailInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteDetailInformationRepositoryImpl @Inject constructor(
    val dao : TrailPorTalService
) : RemoteDetailInformationRepository {
    override suspend fun getPlatformAtTheEntranceData(
        key: String,
        railCode: String,
        lineCd : String,
        stinCode: String
    ): Flow<DomainPlatformEntrance> =
        flowOf(
            dao.getPlatformAtTheEntranceData(key ,"json" ,railCode ,lineCd , stinCode)
        ).map {
            it.RemoteToDomain()
        }
}