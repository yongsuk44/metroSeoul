package com.young.domain.usecase.subwayfacilities.Cache

import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.subwayfacilities.CacheSubWayFacilitiesRepository
import com.young.domain.usecase.base.BaseUseCase
import javax.inject.Inject

typealias InsertSubWayFacilitiesDataBaseUseCase = BaseUseCase<List<DomainSubwayFacilities>, Unit>

class InsertSubWayFacilitiesDataUseCase @Inject constructor(
    private val Cache : CacheSubWayFacilitiesRepository
) : InsertSubWayFacilitiesDataBaseUseCase {

    override suspend operator fun invoke(param : List<DomainSubwayFacilities>) : Unit =
        Cache.insertSubWayFacilitiesDataAll(param)
}