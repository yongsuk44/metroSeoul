package com.young.domain.timetable

import com.young.domain.factory.timetableFactory
import com.young.domain.model.DomainAllStationCodes
import com.young.domain.model.Row
import com.young.domain.repository.location.LocalAllStationCodesRepository
import com.young.domain.usecase.local.LocalAllStationCodeBaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface FakeFindStationCodeRepository : LocalAllStationCodesRepository {
    override suspend fun insert(items: DomainAllStationCodes) {}

    override suspend fun findStationCode(code: String): Flow<Row?> = flow {
        emit(timetableFactory.generateRowData())
    }
}