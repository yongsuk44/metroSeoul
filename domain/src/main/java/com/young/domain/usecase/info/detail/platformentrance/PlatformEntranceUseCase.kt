package com.young.domain.usecase.info.detail.platformentrance

import com.young.domain.model.DomainPlatformEntrance
import com.young.domain.repository.informationdetail.remote.RemoteDetailInformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PlatFormEntranceBaseUseCase {
    suspend fun getPlatformAtTheEntranceData(key : String , railCode : String ,lineCd : String, stinCode : String) : Flow<DomainPlatformEntrance>
}
class PlatformEntranceUseCase @Inject constructor(
    val remote : RemoteDetailInformationRepository
) : PlatFormEntranceBaseUseCase {
    override suspend fun getPlatformAtTheEntranceData(
        key: String,
        railCode: String,
        lineCd : String,
        stinCode: String
    ): Flow<DomainPlatformEntrance> =
        remote.getPlatformAtTheEntranceData(key, railCode, lineCd, stinCode)

}