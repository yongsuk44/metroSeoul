package com.young.domain.usecase.info.basic.local

import com.young.domain.model.AllRouteInformation
import com.young.domain.repository.information.local.LocalFullRouteInformationRepository
import com.young.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

typealias LocalInsertFullRouteInformationBaseUseCase = BaseUseCase<List<AllRouteInformation>, Flow<Unit>>

class LocalInsertFullRouteInformationUseCase @Inject constructor(
    private val local : LocalFullRouteInformationRepository
) : LocalInsertFullRouteInformationBaseUseCase {

    override suspend operator fun invoke(param : List<AllRouteInformation>) : Flow<Unit> =
        flowOf(local.insert(param))
}