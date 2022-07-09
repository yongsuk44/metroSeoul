package com.young.domain.usecase.subwayfacilities

import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.SubWayFacilitiesRepository
import com.young.domain.usecase.base.BaseUseCase
import javax.inject.Inject

typealias UpdateSubWayFacilitiesDataBaseUseCase = BaseUseCase<List<DomainSubwayFacilities>, Unit>

class UpdateSubWayFacilitiesDataUseCase @Inject constructor(
    private val repository : SubWayFacilitiesRepository
) : UpdateSubWayFacilitiesDataBaseUseCase {

    override suspend operator fun invoke(param : List<DomainSubwayFacilities>) : Unit =
        repository.updateSubWayFacilitiesData(param)
}