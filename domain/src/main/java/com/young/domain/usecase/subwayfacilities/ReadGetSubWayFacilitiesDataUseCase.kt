package com.young.domain.usecase.subwayfacilities

import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.SubWayFacilitiesRepository
import com.young.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias ReadGetSubWayFacilitiesDataBaseUseCase = BaseUseCase<Unit, Flow<List<DomainSubwayFacilities>>>

class ReadGetSubWayFacilitiesDataUseCase @Inject constructor(
    private val repository : SubWayFacilitiesRepository
) : ReadGetSubWayFacilitiesDataBaseUseCase {
    override suspend fun invoke(param: Unit): Flow<List<DomainSubwayFacilities>> =
        repository.readCacheAllData()
}