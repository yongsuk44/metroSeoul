package com.young.domain.usecase.remote

import com.young.domain.model.DomainPlatformEntrance
import com.young.domain.repository.remote.RemoteDetailInformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PlatFormEntranceBaseUseCase {
    suspend fun getPlatformEntranceData(key : String , railCode : String ,lineCd : String, stinCode : String) : Flow<DomainPlatformEntrance>
}
class PlatformEntranceUseCase @Inject constructor(
    val remote : RemoteDetailInformationRepository
) : PlatFormEntranceBaseUseCase {
    override suspend fun getPlatformEntranceData(
        key: String,
        railCode: String,
        lineCd : String,
        stinCode: String
    ): Flow<DomainPlatformEntrance> =
        remote.getPlatformEntranceData(key, railCode, lineCd, stinCode)

}