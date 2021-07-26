package com.young.domain.usecase.info.information

import com.young.domain.model.AllRouteInformation
import com.young.domain.repository.information.local.LocalFullRouteInformationRepository
import com.young.domain.usecase.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

typealias LocalGetFullRouteInformationBaseUseCase = BaseUseCase<Unit , Flow<List<AllRouteInformation>>>

class LocalGetFullRouteInformationUseCase @Inject constructor(
    private val local : LocalFullRouteInformationRepository
) : LocalGetFullRouteInformationBaseUseCase {
    override suspend fun invoke(param: Unit): Flow<List<AllRouteInformation>> =
        local.getAllData()

    suspend fun getDataSize() : Flow<Int> =
        local.getDataSize()
}