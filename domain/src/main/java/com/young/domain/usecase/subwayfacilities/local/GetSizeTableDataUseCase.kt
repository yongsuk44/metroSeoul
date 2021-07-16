package com.young.domain.usecase.subwayfacilities.local

import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import com.young.domain.usecase.BaseUseCase
import javax.inject.Inject

typealias GetSizeTableDataBaseUseCase = BaseUseCase<Unit , Int>

class GetSizeTableDataUseCase @Inject constructor(
    private val local : LocalSubWayFacilitiesRepository
) : GetSizeTableDataBaseUseCase {
    override suspend fun invoke(param: Unit): Int = local.getDataSize()

}