package com.young.metro

import android.app.Application
import com.young.metro.util.DebugTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MetroApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(DebugTree())
    }
}