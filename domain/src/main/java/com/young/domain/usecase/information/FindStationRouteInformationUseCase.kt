package com.young.domain.usecase.information

import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.repository.FullRouteInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

interface FindStationRouteInformationBaseUseCase {
    suspend fun onFindStationRouteInformation(key: String) : Flow<List<DomainFullRouteInformationBody>>
}

class FindStationRouteInformationUseCase @Inject constructor(
    private val fullRouteInformationRepository: FullRouteInformationRepository,
    private val insertRouteInformationUseCase: InsertRouteInformationBaseUseCase
) : FindStationRouteInformationBaseUseCase {
    override suspend fun onFindStationRouteInformation(key: String): Flow<List<DomainFullRouteInformationBody>> =
        fullRouteInformationRepository.readDataSize()
            .flatMapConcat { size ->
                if (size > 0) fullRouteInformationRepository.findStationRouteInformation(null)
                else insertRouteInformationUseCase.onInsertRouteInformation(key)
            }

}