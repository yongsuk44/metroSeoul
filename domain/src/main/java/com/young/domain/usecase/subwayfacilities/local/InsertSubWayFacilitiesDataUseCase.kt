package com.young.domain.usecase.subwayfacilities.local

import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import com.young.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias InsertSubWayFacilitiesDataBaseUseCase = BaseUseCase<List<DomainSubwayFacilities>,Unit>

class InsertSubWayFacilitiesDataUseCase @Inject constructor(
    private val local : LocalSubWayFacilitiesRepository
) : InsertSubWayFacilitiesDataBaseUseCase {

    override suspend operator fun invoke(param : List<DomainSubwayFacilities>) : Unit =
        local.insertSubWayFacilitiesDataAll(param)
}