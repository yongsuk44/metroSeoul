package com.young.domain.di

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object GoogleLocationServiceModule {

    @Provides
    @Singleton
    fun provideLocationService(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun provideLocationRequest() : LocationRequest =
        LocationRequest.create().apply {
            interval = 5 * 1000L
            fastestInterval = 1 * 1000L
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

    @Provides
    @Singleton
    fun provideGeoCoder(@ApplicationContext context: Context): Geocoder = Geocoder(context)
}