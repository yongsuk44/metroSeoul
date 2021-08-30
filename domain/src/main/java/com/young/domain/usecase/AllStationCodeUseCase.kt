package com.young.domain.usecase

import com.young.domain.model.DomainRow
import com.young.domain.repository.location.CacheAllStationCodesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CacheAllStationCodeBaseUseCase {
    suspend fun insert(items : List<DomainRow>) : Flow<List<Long>>
    suspend fun findStationCode(code : String) : Flow<DomainRow?>
}

class AllStationCodeUseCase @Inject constructor(
    private val cache : CacheAllStationCodesRepository
) : CacheAllStationCodeBaseUseCase {
    override suspend fun insert(items: List<DomainRow>) : Flow<List<Long>> =
        cache.insert(items)


    override suspend fun findStationCode(code : String): Flow<DomainRow?> =
        cache.findStationCode(code)
}