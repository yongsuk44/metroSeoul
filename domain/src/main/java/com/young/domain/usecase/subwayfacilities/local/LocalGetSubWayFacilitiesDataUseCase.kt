package com.young.domain.usecase.subwayfacilities.local

import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import com.young.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias LocalGetSubWayFacilitiesDataBaseUseCase = BaseUseCase<Unit, Flow<List<DomainSubwayFacilities>>>

class LocalGetSubWayFacilitiesDataUseCase @Inject constructor(
    private val local : LocalSubWayFacilitiesRepository
) : LocalGetSubWayFacilitiesDataBaseUseCase {
    override suspend fun invoke(param: Unit): Flow<List<DomainSubwayFacilities>> =
        local.getLocalAllData()
}