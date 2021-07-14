package com.young.domain.usecase.subwayfacilities

import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.subwayfacilities.RemoteSubWayFacilitiesRepository
import com.young.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetSubWayFacilitiesDataBaseUseCase = BaseUseCase<String , Flow<List<DomainSubwayFacilities>>>

class GetSubWayFacilitiesDataUseCase @Inject constructor(
    private val remote : RemoteSubWayFacilitiesRepository
) : GetSubWayFacilitiesDataBaseUseCase {
    override suspend operator fun invoke(param : String) : Flow<List<DomainSubwayFacilities>> =
        remote.getSubWayFacilitiesData(param)
}