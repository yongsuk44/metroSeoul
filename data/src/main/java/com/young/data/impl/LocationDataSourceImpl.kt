package com.young.data.impl

import com.young.data.mapper.DataToDomainMapper.DataToDomain
import com.young.domain.model.DomainLocationTrailData
import com.young.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationDataSourceImpl @Inject constructor(
    private val remote: com.young.data.datasource.remote.RemoteLocationDataSource
) : LocationRepository {
    override suspend fun getStationAddress(
        key: String,
        railCdoe: String,
        lineCode: String
    ): Flow<DomainLocationTrailData> =
        remote.getStationAddress(key, railCdoe, lineCode).map { it.DataToDomain() }
}