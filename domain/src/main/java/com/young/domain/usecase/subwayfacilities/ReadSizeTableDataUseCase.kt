package com.young.domain.usecase.subwayfacilities

import com.young.domain.repository.SubWayFacilitiesRepository
import com.young.domain.usecase.base.BaseUseCase
import javax.inject.Inject

typealias ReadSizeTableDataBaseUseCase = BaseUseCase<Unit, Int>

class ReadSizeTableDataUseCase @Inject constructor(
    private val repository : SubWayFacilitiesRepository
) : ReadSizeTableDataBaseUseCase {
    override suspend fun invoke(param: Unit): Int = repository.readDataSize()

}