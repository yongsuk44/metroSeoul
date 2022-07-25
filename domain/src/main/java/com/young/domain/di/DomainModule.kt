package com.young.domain.di

import com.young.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DomainModule {

    @Binds abstract fun provideStationData(stationDataUseCase: StationDataUseCase): StationDataBaseUseCase
    @Binds abstract fun provideAllStationCodes(allStationCodeUseCase: AllStationCodeUseCase): AllStationCodeBaseUseCase
    @Binds abstract fun provideCoordinate(coordinateUseCase: CoordinateUseCase): CoordinateBaseUseCase
    @Binds abstract fun provideLocation(locationUseCase: LocationUseCase): LocationBaseUseCase
}
