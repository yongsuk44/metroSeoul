package com.young.domain.usecase.info.basic.local

import com.young.domain.model.AllRouteInformation
import com.young.domain.repository.information.local.LocalFullRouteInformationRepository
import com.young.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

typealias LocalGetStationBaseUseCase = BaseUseCase<List<String> , Flow<List<AllRouteInformation>>>

class LocalGetStationDataUseCase @Inject constructor(
    val local : LocalFullRouteInformationRepository
) : LocalGetStationBaseUseCase {
    override suspend fun invoke(param: List<String>): Flow<List<AllRouteInformation>> =
        local.getStationData(param)

}