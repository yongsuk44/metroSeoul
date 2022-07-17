package com.young.domain.usecase.location

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.young.base.exception.LocationPermissionException
import com.young.base.exception.LocationUpdateException
import com.young.domain.model.DomainUserLocationData
import com.young.domain.model.DomainUserLocationData.Companion.toMapper
import com.young.domain.usecase.permission.PermissionLocationBaseUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

interface UpdateLocationServiceBaseUseCase {
    suspend fun updateLocationService() : Flow<DomainUserLocationData>
}

class UpdateLocationServiceUseCase @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationRequest: LocationRequest,
    private val permissionLocationUseCase: PermissionLocationBaseUseCase,
    private val updateLastLocationBaseUseCase: UpdateLastLocationBaseUseCase
) : UpdateLocationServiceBaseUseCase {

    @SuppressLint("MissingPermission")
    override suspend fun updateLocationService() = callbackFlow {
        when {
            permissionLocationUseCase() -> {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, requestCallBack { offer(it) }, Looper.getMainLooper())
                    .addOnSuccessListener {  }
                    .addOnFailureListener { close(LocationUpdateException("Location Update Error $it")) }
            }

            else -> close(LocationPermissionException("Permission Error"))
        }

        awaitClose { fusedLocationProviderClient.removeLocationUpdates(requestCallBack {} ) }

    }.flatMapConcat { location ->
        updateLastLocationBaseUseCase.updateLastLocation(location.toMapper())
    }

    private fun requestCallBack(send: (Location) -> Unit) = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            Timber.d("Update Latitude : ${result.lastLocation.latitude} \nUpdate Longitude : ${result.lastLocation.longitude}")
            send(result.lastLocation)
        }
    }
}