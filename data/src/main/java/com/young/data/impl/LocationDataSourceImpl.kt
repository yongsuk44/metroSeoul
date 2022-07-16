package com.young.data.impl

import com.google.firebase.database.FirebaseDatabase
import com.young.data.mapper.DataToDomainMapper.DataToDomain
import com.young.domain.model.DomainLocationTrailData
import com.young.domain.model.DomainStationNameAndMapXY
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

    override suspend fun getFirebaseStationLocation(firebaseDatabase: FirebaseDatabase): Flow<List<DomainStationNameAndMapXY>> =
        remote.getFirebaseStationLocation(firebaseDatabase)
            .map {
                it.map { it.DataToDomain() }
            }
}