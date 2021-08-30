package com.young.cache.cache.impl.cache

import com.young.cache.cache.datasource.cache.CacheAllStationCodesDataSource
import com.young.cache.cache.mapper.DataToDomainMapper.DataToDomain
import com.young.cache.cache.mapper.DomainToDataMapper.DomainToData
import com.young.domain.model.DomainRow
import com.young.domain.repository.location.CacheAllStationCodesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheAllStationCodesDataSourceImpl @Inject constructor(
    private val datasource : CacheAllStationCodesDataSource
) : CacheAllStationCodesRepository {

    override suspend fun insert(items: List<DomainRow>) : Flow<List<Long>> = datasource.insert(items.map { it.DomainToData() })

    override suspend fun findStationCode(code: String): Flow<DomainRow?> = datasource.findStationCode(code).map { it?.DataToDomain() }
}