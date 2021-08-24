package com.young.domain.usecase.subwayfacilities.Cache

import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.subwayfacilities.CacheSubWayFacilitiesRepository
import com.young.domain.usecase.base.BaseUseCase
import javax.inject.Inject

typealias UpdateSubWayFacilitiesDataBaseUseCase = BaseUseCase<List<DomainSubwayFacilities>, Unit>

class UpdateSubWayFacilitiesDataUseCase @Inject constructor(
    private val Cache : CacheSubWayFacilitiesRepository
) : UpdateSubWayFacilitiesDataBaseUseCase {

    override suspend operator fun invoke(param : List<DomainSubwayFacilities>) : Unit =
        Cache.updateSubWayFacilitiesData(param)
}