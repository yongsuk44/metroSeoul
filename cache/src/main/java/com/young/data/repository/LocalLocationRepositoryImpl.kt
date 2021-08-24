package com.young.data.repository

import com.young.data.dao.FullRouteInformationDao
import com.young.data.model.LocalTrailCodeAndLineCode
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainTrailCodeAndLineCode
import com.young.domain.repository.location.LocalLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalLocationRepositoryImpl @Inject constructor(
    private val dao: FullRouteInformationDao
) : LocalLocationRepository {
    override suspend fun getTrailCodeAndLineCode(): Flow<List<DomainTrailCodeAndLineCode>> =
        flowOf(dao.getTrailCodeAllData())
            .map {
                BaseMapper.setList(BaseMapper<LocalTrailCodeAndLineCode, DomainTrailCodeAndLineCode>())
                    .run {
                        this(it)
                    }
            }

}