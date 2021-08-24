package com.young.domain.usecase

import com.young.domain.model.LocationData
import com.young.domain.repository.remote.RemoteLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface RemoteLocationBaseUseCase {
    suspend fun getStationAddress(key : String , railCdoe : String , lineCode : String) : Flow<List<LocationData>>
}

class LocationUseCase @Inject constructor(
    private val remote: RemoteLocationRepository
) : RemoteLocationBaseUseCase {

    override suspend fun getStationAddress(
        key: String,
        railCdoe : String ,
        lineCode : String
    ): Flow<List<LocationData>> {
        return remote.getStationAddress(key, railCdoe, lineCode)
    }

}