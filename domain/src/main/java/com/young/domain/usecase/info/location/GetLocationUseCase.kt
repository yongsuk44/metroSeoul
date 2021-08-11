package com.young.domain.usecase.info.location

import com.young.domain.model.DomainTrailCodeAndLineCode
import com.young.domain.model.LocationData
import com.young.domain.repository.location.LocalLocationRepository
import com.young.domain.repository.remote.RemoteLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface GetLocationBaseUseCase {
    suspend fun getStationAddress(key : String , railCdoe : String , lineCode : String) : Flow<List<LocationData>>
    suspend fun getTrailCodeAndLineCode() : Flow<List<DomainTrailCodeAndLineCode>>
}

class GetLocationUseCase @Inject constructor(
    private val remote: RemoteLocationRepository,
    private val local: LocalLocationRepository
) : GetLocationBaseUseCase {

    override suspend fun getStationAddress(
        key: String,
        railCdoe : String ,
        lineCode : String
    ): Flow<List<LocationData>> {
        return remote.getStationAddress(key, railCdoe, lineCode)
    }

    override suspend fun getTrailCodeAndLineCode(): Flow<List<DomainTrailCodeAndLineCode>> =
        local.getTrailCodeAndLineCode()
}