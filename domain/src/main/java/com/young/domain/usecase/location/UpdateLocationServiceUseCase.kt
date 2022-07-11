package com.young.domain.usecase.location

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.young.base.locationPermissionList
import timber.log.Timber

internal typealias UpdateLocationServiceBaseUseCase = () -> Unit

class UpdateLocationServiceUseCase constructor(
    private val context: Context,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val locationRequest: LocationRequest
) : UpdateLocationServiceBaseUseCase {

    @SuppressLint("MissingPermission")
    override fun invoke() {

        locationPermissionList.any { permission ->
            context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }.also { verifyPermission ->
            if (verifyPermission) {
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    requestCallBack,
                    Looper.getMainLooper()
                )
            }
        }
    }

    private val requestCallBack = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            Timber.d("Update Latitude : ${result.lastLocation.latitude} \nUpdate Longitude : ${result.lastLocation.longitude}")
            fusedLocationProviderClient.removeLocationUpdates(this)
        }
    }
}