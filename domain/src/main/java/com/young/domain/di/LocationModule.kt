package com.young.domain.di

import android.location.Geocoder
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.young.domain.repository.LocationRepository
import com.young.domain.usecase.CoordinateBaseUseCase
import com.young.domain.usecase.location.*
import com.young.domain.usecase.permission.PermissionLocationUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
abstract class LocationModule {

    @Binds
    abstract fun providesReadLocationService(readLocationServiceUseCase: ReadLocationServiceUseCase) : ReadLocationServiceBaseUseCase

    @Binds
    abstract fun providesUpdateLocationService(updateLocationServiceUseCase: UpdateLocationServiceUseCase) : UpdateLocationServiceBaseUseCase

    @Binds
    abstract fun providesGetStationCoordinateData(getStationCoordinateDataUseCase: GetStationCoordinateDataUseCase) : GetStationCoordinateDataBaseUseCase
}