package com.young.domain.usecase

import com.young.domain.model.DomainLocationTrailData
import com.young.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocationBaseUseCase {
    suspend fun getStationAddress(key : String , railCdoe : String , lineCode : String) : Flow<DomainLocationTrailData>
}

class LocationUseCase @Inject constructor(
    private val remote: LocationRepository
) : LocationBaseUseCase {

    override suspend fun getStationAddress(
        key: String,
        railCdoe : String ,
        lineCode : String
    ): Flow<DomainLocationTrailData> {
        return remote.getStationAddress(key, railCdoe, lineCode)
    }

}