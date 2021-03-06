package com.young.domain.fake

import com.young.domain.factory.ModelFactory.generateRowData
import com.young.domain.model.DomainRow
import com.young.domain.repository.AllStationCodesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface FakeFindStationCodeRepository : AllStationCodesRepository {
    override suspend fun insert(items: List<DomainRow>): Flow<List<Long>> = flow {
        emit(listOf<Long>(1,1,1,1))
    }

    override suspend fun findStationCode(code: String): Flow<DomainRow?> = flow {
        emit(generateRowData())
    }
}