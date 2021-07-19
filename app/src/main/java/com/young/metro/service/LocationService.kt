package com.young.metro.service

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Binder
import android.os.IBinder
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import javax.inject.Inject

class LocationService : Service() {

    private lateinit var locationCallBack : LocationCallback

    override fun onCreate() {
        super.onCreate()

        LocationServices.getFusedLocationProviderClient(this).run {


        }
    }

    override fun onBind(p0: Intent?): IBinder {
        stopForeground(true)
        return LocalBinder()
    }

    inner class LocalBinder : Binder() {
        internal val service : LocationService
            get() = this@LocationService
    }
}