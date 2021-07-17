package com.young.domain.usecase.subwayfacilities.remote

import com.young.domain.model.DomainSubWayTel
import com.young.domain.repository.subwaytel.RemoteSubWayTelRepository
import com.young.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias RemoteGetSubWayTelBaseUseCase = BaseUseCase<String , Flow<List<DomainSubWayTel>>>

class RemoteGetSubWayTelUseCase @Inject constructor(
    private val remote : RemoteSubWayTelRepository
) : RemoteGetSubWayTelBaseUseCase {
    override suspend fun invoke(param: String): Flow<List<DomainSubWayTel>> =
        remote.getSubWayTelData(param)
}