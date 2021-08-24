package com.young.cache.repository

import com.young.cache.dao.FullRouteInformationDao
import com.young.cache.model.CacheTrailCodeAndLineCode
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainTrailCodeAndLineCode
import com.young.domain.repository.location.CacheLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheLocationRepositoryImpl @Inject constructor(
    private val dao: FullRouteInformationDao
) : CacheLocationRepository {
    override suspend fun getTrailCodeAndLineCode(): Flow<List<DomainTrailCodeAndLineCode>> =
        flowOf(dao.getTrailCodeAllData())
            .map {
                BaseMapper.setList(BaseMapper<CacheTrailCodeAndLineCode, DomainTrailCodeAndLineCode>())
                    .run {
                        this(it)
                    }
            }

}