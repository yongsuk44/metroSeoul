package com.young.domain.usecase.location

import com.google.firebase.database.FirebaseDatabase
import com.young.domain.repository.LocationRepository
import com.young.domain.usecase.CoordinateBaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetStationCoordinateDataBaseUseCase {
    suspend fun insertStationCoordinateData(firebaseDatabase: FirebaseDatabase) : Flow<Boolean>
}

class GetStationCoordinateDataUseCase @Inject constructor(
    private val locationRepository: LocationRepository,
    private val coordinateUseCase: CoordinateBaseUseCase
) : GetStationCoordinateDataBaseUseCase {

    override suspend fun insertStationCoordinateData(firebaseDatabase: FirebaseDatabase) =
        locationRepository.getFirebaseStationLocation(firebaseDatabase)
            .flatMapConcat { coordinateUseCase.insertStationCoordinateData(it) }
            .map { it.all { it > 0 } }

}