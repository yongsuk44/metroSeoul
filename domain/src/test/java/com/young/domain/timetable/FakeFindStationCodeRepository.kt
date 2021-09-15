package com.young.domain.timetable

import com.young.domain.factory.timetableFactory
import com.young.domain.model.DomainAllStationCodes
import com.young.domain.model.DomainRow
import com.young.domain.repository.location.CacheAllStationCodesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface FakeFindStationCodeRepository : CacheAllStationCodesRepository {
    override suspend fun insert(items: List<DomainRow>): Flow<List<Long>> = flow {
        emit(listOf<Long>(1,1,1,1))
    }

    override suspend fun findStationCode(code: String): Flow<DomainRow?> = flow {
        emit(timetableFactory.generateRowData())
    }
}