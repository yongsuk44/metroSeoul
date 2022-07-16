package com.young.data.impl

import com.young.data.datasource.cache.CacheAllStationCodesDataSource
import com.young.data.mapper.DataToDomainMapper.DataToDomain
import com.young.data.mapper.DomainToDataMapper.DomainToData
import com.young.domain.model.DomainRow
import com.young.domain.repository.AllStationCodesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AllStationCodesDataSourceImpl @Inject constructor(
    private val cache : CacheAllStationCodesDataSource
) : AllStationCodesRepository {

    override suspend fun insert(items: List<DomainRow>) : Flow<List<Long>> =
        cache.insert(items.map { it.DomainToData() })

    override suspend fun findStationCode(code: String): Flow<DomainRow?> =
        cache.findStationCode(code).map { it?.DataToDomain() }
}