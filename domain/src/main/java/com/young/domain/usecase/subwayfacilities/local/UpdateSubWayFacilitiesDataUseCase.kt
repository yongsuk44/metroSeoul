package com.young.domain.usecase.subwayfacilities.local

import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import com.young.domain.usecase.BaseUseCase
import javax.inject.Inject

typealias UpdateSubWayFacilitiesDataBaseUseCase = BaseUseCase<List<DomainSubwayFacilities>,Unit>

class UpdateSubWayFacilitiesDataUseCase @Inject constructor(
    private val local : LocalSubWayFacilitiesRepository
) : UpdateSubWayFacilitiesDataBaseUseCase {

    override suspend operator fun invoke(param : List<DomainSubwayFacilities>) : Unit =
        local.updateSubWayFacilitiesData(param)
}