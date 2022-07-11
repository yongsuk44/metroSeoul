package com.young.domain.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object GoogleLocationServiceModule {

    @Provides
    @Reusable
    fun provideLocationService(
        context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Reusable
    fun provideLocationRequest() : LocationRequest =
        LocationRequest.create().apply {
            interval = 5 * 1000L
            fastestInterval = 1 * 1000L
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

}