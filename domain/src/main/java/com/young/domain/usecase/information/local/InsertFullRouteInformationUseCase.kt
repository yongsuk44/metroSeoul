package com.young.domain.usecase.information.local

import com.young.domain.model.AllRouteInformation
import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.information.local.LocalFullRouteInformationRepository
import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import com.young.domain.usecase.BaseUseCase
import javax.inject.Inject

typealias LocalInsertFullRouteInformationBaseUseCase = BaseUseCase<List<AllRouteInformation>, Unit>

class LocalInsertFullRouteInformationUseCase @Inject constructor(
    private val local : LocalFullRouteInformationRepository
) : LocalInsertFullRouteInformationBaseUseCase {

    override suspend operator fun invoke(param : List<AllRouteInformation>) : Unit =
        local.insert(param)
}