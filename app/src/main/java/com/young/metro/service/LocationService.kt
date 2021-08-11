package com.young.metro.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.google.android.gms.location.LocationCallback

class LocationService : Service() {

    private lateinit var locationCallBack : LocationCallback

    override fun onCreate() {
        super.onCreate()
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