package com.young.domain.usecase.subwayfacilities

import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.subwayfacilities.SubWayFacilitiesRepository
import com.young.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias GetSubWayFacilitiesDataBaseUseCase = BaseUseCase<String, Flow<List<DomainSubwayFacilities>>>

class GetSubWayFacilitiesDataUseCase @Inject constructor(
    private val remote : SubWayFacilitiesRepository
) : GetSubWayFacilitiesDataBaseUseCase {
    override suspend operator fun invoke(param : String) : Flow<List<DomainSubwayFacilities>> =
        remote.getSubWayFacilitiesData(param)
}