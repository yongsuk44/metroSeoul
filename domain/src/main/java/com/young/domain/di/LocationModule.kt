package com.young.domain.di

import com.young.domain.usecase.location.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class LocationModule {

    @Binds
    abstract fun providesGetStationCoordinateData(getStationCoordinateDataUseCase: GetStationCoordinateDataUseCase): GetStationCoordinateDataBaseUseCase

    @Binds
    abstract fun providesReadLastLocation(readLastLocationUseCase: ReadLastLocationUseCase): ReadLastLocationBaseUseCase

    @Binds
    abstract fun providesUpdateLastLocation(updateLastLocationUseCase: UpdateLastLocationUseCase): UpdateLastLocationBaseUseCase
}