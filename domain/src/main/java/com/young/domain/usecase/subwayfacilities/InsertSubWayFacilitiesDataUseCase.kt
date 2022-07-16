package com.young.domain.usecase.subwayfacilities

import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.SubWayFacilitiesRepository
import com.young.domain.usecase.base.BaseUseCase
import javax.inject.Inject

typealias InsertSubWayFacilitiesDataBaseUseCase = BaseUseCase<List<DomainSubwayFacilities>, Unit>

class InsertSubWayFacilitiesDataUseCase @Inject constructor(
    private val repository : SubWayFacilitiesRepository
) : InsertSubWayFacilitiesDataBaseUseCase {

    override suspend operator fun invoke(param : List<DomainSubwayFacilities>) : Unit =
        repository.insertSubWayFacilitiesDataAll(param)
}