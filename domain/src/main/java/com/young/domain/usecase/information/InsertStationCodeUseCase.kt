package com.young.domain.usecase.information

import com.young.domain.repository.FullRouteInformationRepository
import com.young.domain.usecase.AllStationCodeUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface InsertStationCodeBaseUseCase {
    suspend fun onInsertStationCode(seoulKey: String) : Flow<Boolean>
}

class InsertStationCodeUseCase @Inject constructor(
    private val routeInformationRepository: FullRouteInformationRepository,
    private val allStationCodeUseCase: AllStationCodeUseCase
) : InsertStationCodeBaseUseCase {

    override suspend fun onInsertStationCode(seoulKey: String): Flow<Boolean> =
        routeInformationRepository.getAllStationCode(seoulKey)
            .flatMapConcat { allStationCodeUseCase.insert(it) }
            .map { it.all { it > 0 } }

}