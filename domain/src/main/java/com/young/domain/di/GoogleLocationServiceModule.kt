package com.young.domain.di

import android.app.Application
import android.content.Context
import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.young.domain.usecase.permission.PermissionLocationBaseUseCase
import com.young.domain.usecase.permission.PermissionLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ActivityComponent::class)
object GoogleLocationServiceModule {

    @Provides
    @Reusable
    fun provideLocationService(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Reusable
    fun provideLocationRequest() : LocationRequest =
        LocationRequest.create().apply {
            interval = 5 * 1000L
            fastestInterval = 1 * 1000L
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

    @Provides
    @Reusable
    fun provideGeoCoder(@ApplicationContext context: Application): Geocoder = Geocoder(context)
}