package com.young.domain.usecase.information

import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.repository.FullRouteInformationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ReadRouteInformationBaseUseCase {
    suspend fun readRouteInformation(stinCodes : List<String>) : Flow<List<DomainFullRouteInformationBody>>
}

class ReadRouteInformationUseCase @Inject constructor(
    private val routeInformationRepository: FullRouteInformationRepository
) : ReadRouteInformationBaseUseCase {

    override suspend fun readRouteInformation(stinCodes: List<String>) =
        routeInformationRepository.readStationData(stinCodes)
}