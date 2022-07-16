package com.young.remote.repository

import com.google.firebase.FirebaseException
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.young.base.FireBaseStationLocationKey
import com.young.data.datasource.remote.RemoteLocationDataSource
import com.young.data.model.DataLocationTrailData
import com.young.data.model.DataStationNameAndMapXY
import com.young.remote.api.TrailPorTalService
import com.young.remote.mapper.RemoteToDataMapper.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class RemoteLocationRepositoryImpl @Inject constructor(
    private val service: TrailPorTalService
) : RemoteLocationDataSource {
    override suspend fun getStationAddress(
        key: String,
        trailCode: String,
        lineCode: String
    ): Flow<DataLocationTrailData> = flowOf(
        service.getStationAddressData(key, "json", trailCode, lineCode).RemoteData()
    )

    override suspend fun getFirebaseStationLocation(firebase: FirebaseDatabase): Flow<List<DataStationNameAndMapXY>> = callbackFlow {
        firebase.reference.child(FireBaseStationLocationKey).get()
            .addOnSuccessListener { snapShot ->
                offer(
                    (snapShot.value as List<String>).map { location ->
                        Gson().fromJson(location, DataStationNameAndMapXY::class.java)
                    }
                )
            }
            .addOnFailureListener {
                close(FirebaseException("Firebase Station Location Data Failed ${it.message}"))
            }
            .addOnCanceledListener {
                close(FirebaseException("Firebase Station Location Data Canceled"))
            }
    }
}