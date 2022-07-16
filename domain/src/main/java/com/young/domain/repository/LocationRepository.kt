package com.young.domain.repository

import com.google.firebase.database.FirebaseDatabase
import com.young.domain.model.DomainLocationTrailData
import com.young.domain.model.DomainStationNameAndMapXY
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    suspend fun getStationAddress(key: String, railCdoe: String, lineCode: String): Flow<DomainLocationTrailData>
    suspend fun getFirebaseStationLocation(firebaseDatabase: FirebaseDatabase): Flow<List<DomainStationNameAndMapXY>>
}