package com.young.data.impl

import com.google.firebase.database.FirebaseDatabase
import com.young.data.datasource.cache.CacheLocationDataSource
import com.young.data.datasource.remote.RemoteLocationDataSource
import com.young.data.mapper.DataToDomainMapper.DataToDomain
import com.young.data.model.DataUserLocationData.Companion.toMapper
import com.young.domain.model.DomainLocationTrailData
import com.young.domain.model.DomainStationNameAndMapXY
import com.young.domain.model.DomainUserLocationData
import com.young.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationDataSourceImpl @Inject constructor(
    private val remote: RemoteLocationDataSource,
    private val local: CacheLocationDataSource
) : LocationRepository {
    override suspend fun getStationAddress(
        key: String,
        railCdoe: String,
        lineCode: String
    ): Flow<DomainLocationTrailData> =
        remote.getStationAddress(key, railCdoe, lineCode).map { it.DataToDomain() }

    override suspend fun getFirebaseStationLocation(firebaseDatabase: FirebaseDatabase): Flow<List<DomainStationNameAndMapXY>> =
        remote.getFirebaseStationLocation(firebaseDatabase)
            .map {
                it.map { it.DataToDomain() }
            }

    override suspend fun readLastLocation(): Flow<DomainUserLocationData> =
        local.readLastLocation().map { it.toMapper() }

    override suspend fun updateLastLocation(domainUserLocationData: DomainUserLocationData): Flow<DomainUserLocationData> =
        local.updateLastLocation(domainUserLocationData.toMapper()).map { it.toMapper() }
}