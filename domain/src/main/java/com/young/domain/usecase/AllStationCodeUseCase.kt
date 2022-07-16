package com.young.domain.usecase

import com.young.domain.model.DomainRow
import com.young.domain.repository.AllStationCodesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface AllStationCodeBaseUseCase {
    suspend fun insert(items : List<DomainRow>) : Flow<List<Long>>
    suspend fun findStationCode(code : String) : Flow<DomainRow?>
}

class AllStationCodeUseCase @Inject constructor(
    private val repository: AllStationCodesRepository
) : AllStationCodeBaseUseCase {
    override suspend fun insert(items: List<DomainRow>) : Flow<List<Long>> =
        repository.insert(items)

    override suspend fun findStationCode(code : String): Flow<DomainRow?> =
        repository.findStationCode(code)
}