package com.young.cache.cache.impl.remote

import com.young.cache.cache.datasource.remote.RemoteLocationDataSource
import com.young.cache.cache.mapper.DataToDomainMapper.DataToDomain
import com.young.domain.model.DomainLocationTrailData
import com.young.domain.repository.remote.RemoteLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteLocationDataSourceImpl @Inject constructor(
    private val datasource : RemoteLocationDataSource
) : RemoteLocationRepository {
    override suspend fun getStationAddress(
        key: String,
        railCdoe: String,
        lineCode: String
    ): Flow<DomainLocationTrailData> =
        datasource.getStationAddress(key , railCdoe , lineCode).map { it.DataToDomain() }
}