package com.young.cache.repository

import com.young.cache.dao.AllStationCodeDao
import com.young.cache.mapper.CacheToDomainMapper.DomainToLocal
import com.young.cache.model.LocalAllStationCodes
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainAllStationCodes
import com.young.domain.model.Row
import com.young.domain.repository.location.LocalAllStationCodesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LocalAllStationCodesRepositoryImpl @Inject constructor(
    private val dao : AllStationCodeDao
) : LocalAllStationCodesRepository {
    override suspend fun insert(items: DomainAllStationCodes) {
        dao.insert(
            items.SearchInfoBySubwayNameService.row.map {
                it.DomainToLocal()
            }
        )
    }

    override suspend fun findStationCode(code: String): Flow<Row?> = flow {
        emit(dao.findStationCodes(code))
    }
        .flowOn(Dispatchers.IO)
        .map {
        it ?: return@map null
        BaseMapper<LocalAllStationCodes, Row>().run {
            this(it)
        }
    }
}