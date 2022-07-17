package com.young.domain.usecase.location

import com.young.domain.model.DomainUserLocationData
import com.young.domain.repository.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ReadLastLocationBaseUseCase {
    suspend fun readLastLocation() : Flow<DomainUserLocationData>
}

class ReadLastLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) : ReadLastLocationBaseUseCase {

    override suspend fun readLastLocation(): Flow<DomainUserLocationData> =
        locationRepository.readLastLocation()
}