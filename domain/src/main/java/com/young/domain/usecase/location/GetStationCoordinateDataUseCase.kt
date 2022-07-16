package com.young.domain.usecase.location

import com.google.firebase.database.FirebaseDatabase
import com.young.domain.repository.LocationRepository
import com.young.domain.usecase.CoordinateUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

interface GetStationCoordinateDataBaseUseCase {
    suspend fun insertStationCoordinateData(firebaseDatabase: FirebaseDatabase) : Flow<Boolean>
}

class GetStationCoordinateDataUseCase constructor(
    private val locationRepository: LocationRepository,
    private val coordinateUseCase: CoordinateUseCase
) : GetStationCoordinateDataBaseUseCase {

    override suspend fun insertStationCoordinateData(firebaseDatabase: FirebaseDatabase) =
        locationRepository.getFirebaseStationLocation(firebaseDatabase)
            .flatMapConcat { coordinateUseCase.insertStationCoordinateData(it) }
            .map { it.all { it > 0 } }

}