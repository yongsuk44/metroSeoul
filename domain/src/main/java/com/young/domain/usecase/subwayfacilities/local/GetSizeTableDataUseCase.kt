package com.young.domain.usecase.subwayfacilities.Cache

import com.young.domain.repository.subwayfacilities.CacheSubWayFacilitiesRepository
import com.young.domain.usecase.base.BaseUseCase
import javax.inject.Inject

typealias GetSizeTableDataBaseUseCase = BaseUseCase<Unit, Int>

class GetSizeTableDataUseCase @Inject constructor(
    private val Cache : CacheSubWayFacilitiesRepository
) : GetSizeTableDataBaseUseCase {
    override suspend fun invoke(param: Unit): Int = Cache.getDataSize()

}