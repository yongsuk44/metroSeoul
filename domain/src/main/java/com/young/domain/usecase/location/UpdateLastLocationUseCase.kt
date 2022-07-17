package com.young.domain.usecase.location

import com.young.domain.model.DomainUserLocationData
import com.young.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface UpdateLastLocationBaseUseCase {
    suspend fun updateLastLocation(userLocationData: DomainUserLocationData) : Flow<DomainUserLocationData>
}

class UpdateLastLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) : UpdateLastLocationBaseUseCase {

    override suspend fun updateLastLocation(userLocationData: DomainUserLocationData): Flow<DomainUserLocationData> =
        locationRepository.updateLastLocation(userLocationData)
}