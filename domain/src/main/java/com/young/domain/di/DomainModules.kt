package com.young.domain.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.young.domain.usecase.location.UpdateLastLocationBaseUseCase
import com.young.domain.usecase.location.UpdateLocationServiceBaseUseCase
import com.young.domain.usecase.location.UpdateLocationServiceUseCase
import com.young.domain.usecase.permission.PermissionLocationBaseUseCase
import com.young.domain.usecase.permission.PermissionLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ApplicationComponent::class)
object DomainModules {

    @Provides
    @Reusable
    fun providePermission(
        @ApplicationContext context: Context
    ): PermissionLocationBaseUseCase =
        PermissionLocationUseCase(context)

    @Provides
    @Reusable
    fun providesUpdateLocationService(
        fusedLocationProviderClient: FusedLocationProviderClient,
        locationRequest: LocationRequest,
        permissionLocationUseCase: PermissionLocationBaseUseCase,
        updateLastLocationBaseUseCase: UpdateLastLocationBaseUseCase
    ) : UpdateLocationServiceBaseUseCase =
        UpdateLocationServiceUseCase(fusedLocationProviderClient, locationRequest, permissionLocationUseCase, updateLastLocationBaseUseCase)
}