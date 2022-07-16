package com.young.data.datasource.remote

import com.google.firebase.database.FirebaseDatabase
import com.young.data.model.DataLocationTrailData
import com.young.data.model.DataStationNameAndMapXY
import kotlinx.coroutines.flow.Flow

interface RemoteLocationDataSource {
    suspend fun getStationAddress(
        key: String,
        trailCode: String,
        lineCode: String
    ): Flow<DataLocationTrailData>

    suspend fun getFirebaseStationLocation(firebaseDatabase: FirebaseDatabase): Flow<List<DataStationNameAndMapXY>>
}