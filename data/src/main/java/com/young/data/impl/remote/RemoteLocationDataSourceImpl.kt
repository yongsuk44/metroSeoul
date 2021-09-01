package com.young.data.impl.remote

import com.young.data.mapper.DataToDomainMapper.DataToDomain
import com.young.domain.model.DomainLocationTrailData
import com.young.domain.repository.remote.RemoteLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteLocationDataSourceImpl @Inject constructor(
    private val datasource : com.young.data.datasource.remote.RemoteLocationDataSource
) : RemoteLocationRepository {
    override suspend fun getStationAddress(
        key: String,
        railCdoe: String,
        lineCode: String
    ): Flow<DomainLocationTrailData> =
        datasource.getStationAddress(key , railCdoe , lineCode).map { it.DataToDomain() }
}