package com.young.domain.usecase.subwayfacilities.Cache

import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.subwayfacilities.CacheSubWayFacilitiesRepository
import com.young.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias CacheGetSubWayFacilitiesDataBaseUseCase = BaseUseCase<Unit, Flow<List<DomainSubwayFacilities>>>

class CacheGetSubWayFacilitiesDataUseCase @Inject constructor(
    private val Cache : CacheSubWayFacilitiesRepository
) : CacheGetSubWayFacilitiesDataBaseUseCase {
    override suspend fun invoke(param: Unit): Flow<List<DomainSubwayFacilities>> =
        Cache.getCacheAllData()
}