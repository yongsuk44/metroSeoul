package com.young.domain.usecase.location

import android.annotation.SuppressLint
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.young.base.exception.LocationPermissionException
import com.young.base.exception.LocationUpdateException
import com.young.domain.usecase.permission.PermissionLocationBaseUseCase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import timber.log.Timber
import javax.inject.Inject

interface UpdateLocationServiceBaseUseCase {
    fun updateLocationService() : Flow<Boolean>
}

class UpdateLocationServiceUseCase @Inject constructor(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationRequest: LocationRequest,
    private val permissionLocationUseCase: PermissionLocationBaseUseCase
) : UpdateLocationServiceBaseUseCase {

    @SuppressLint("MissingPermission")
    override fun updateLocationService() = callbackFlow {
        when {
            permissionLocationUseCase() -> {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, requestCallBack, Looper.getMainLooper())
                    .addOnSuccessListener { offer(true) }
                    .addOnFailureListener { close(LocationUpdateException("Location Update Error $it")) }
            }

            else -> close(LocationPermissionException("Permission Error"))
        }
        awaitClose()
    }


    private val requestCallBack = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            Timber.d("Update Latitude : ${result.lastLocation.latitude} \nUpdate Longitude : ${result.lastLocation.longitude}")
            fusedLocationProviderClient.removeLocationUpdates(this)
        }
    }
}