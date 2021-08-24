package com.young.domain.usecase.subwayfacilities.remote

import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.subwayfacilities.RemoteSubWayFacilitiesRepository
import com.young.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias RemoteGetSubWayFacilitiesDataBaseUseCase = BaseUseCase<String, Flow<List<DomainSubwayFacilities>>>

class RemoteGetSubWayFacilitiesDataUseCase @Inject constructor(
    private val remote : RemoteSubWayFacilitiesRepository
) : RemoteGetSubWayFacilitiesDataBaseUseCase {
    override suspend operator fun invoke(param : String) : Flow<List<DomainSubwayFacilities>> =
        remote.getSubWayFacilitiesData(param)
}