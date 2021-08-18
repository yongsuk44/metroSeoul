package com.young.domain.usecase.local

import com.young.domain.model.DomainAllStationCodes
import com.young.domain.model.Row
import com.young.domain.repository.location.LocalAllStationCodesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalAllStationCodeBaseUseCase {
    suspend fun insert(items : DomainAllStationCodes)
    suspend fun findStationCode(code : String) : Flow<Row?>
}
class LocalAllStationCodeUseCase @Inject constructor(
    private val local : LocalAllStationCodesRepository
) : LocalAllStationCodeBaseUseCase {
    override suspend fun insert(items: DomainAllStationCodes) {
        local.insert(items)
    }

    override suspend fun findStationCode(code : String): Flow<Row?> =
        local.findStationCode(code)
}