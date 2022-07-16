package com.young.domain.usecase.information

import com.young.base.exception.RoomInsertException
import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.model.DomainTrailCodeAndLineCode
import com.young.domain.repository.FullRouteInformationRepository
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface InsertRouteInformationBaseUseCase {
    suspend fun onInsertRouteInformation(key: String): Flow<List<DomainFullRouteInformationBody>>
}

class InsertRouteInformationUseCase @Inject constructor(
    private val routeInformationRepository: FullRouteInformationRepository
) : InsertRouteInformationBaseUseCase {

    override suspend fun onInsertRouteInformation(key: String): Flow<List<DomainFullRouteInformationBody>> =
        routeInformationRepository.findStationRouteInformation(key)
            .flatMapConcat { list ->

                routeInformationRepository.insert(list).zip(onInsertLineAndTrailCode(list)) { insert1, insert2 ->
                    if (insert1.all { it > 0 } && insert2.all { it > 0 }) list
                    else throw RoomInsertException("Route Information Insert Error")
                }
            }

    private suspend fun onInsertLineAndTrailCode(list: List<DomainFullRouteInformationBody>) =
        flowOf(list)
            .map { it.map { DomainTrailCodeAndLineCode(it.railOprIsttCd, it.lnCd) } }
            .mapLatest { it.distinctBy { it.lnCd to it.railOprIsttCd } }
            .flatMapConcat { routeInformationRepository.insertLineCodeAndTrailCode(it) }
}