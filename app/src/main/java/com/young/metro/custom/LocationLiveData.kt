package com.young.metro.custom

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.young.base.locationPermissionList
import timber.log.Timber

class LocationLiveData (
    val context: Context,
    private val locationRequest: LocationRequest,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : LiveData<Location>() {

    @SuppressLint("MissingPermission")
    override fun onActive() {
        super.onActive()

        if (locationPermissionList.all { context.checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED }) {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, requestCallBack, Looper.getMainLooper())
                    .addOnSuccessListener { }
                    .addOnFailureListener { }
        }
    }

    override fun onInactive() {
        super.onInactive()
        fusedLocationProviderClient.removeLocationUpdates(requestCallBack)
    }


    private val requestCallBack = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            super.onLocationResult(result)
            Timber.d("Update Latitude : ${result.lastLocation.latitude} \nUpdate Longitude : ${result.lastLocation.longitude}")
            value = result.lastLocation
        }
    }
}