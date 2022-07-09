package com.young.domain.di

import com.young.domain.repository.FullRouteInformationRepository
import com.young.domain.repository.AllStationCodesRepository
import com.young.domain.repository.StationCoordinatesRepository
import com.young.domain.repository.LocationRepository
import com.young.domain.repository.StationDataRepository
import com.young.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
object DomainModule {

    @Provides
    @Reusable
    fun provideStationData(
        fullRouteInformationRepository: FullRouteInformationRepository,
        remote: StationDataRepository
    ) = StationDataUseCase(fullRouteInformationRepository, remote)

    @Provides
    @Reusable
    fun provideAllStationCodes(
        allStationCodesRepository: AllStationCodesRepository
    ) = AllStationCodeUseCase(allStationCodesRepository)

    @Provides
    @Reusable
    fun provideCoordinate(
        cache : StationCoordinatesRepository
    ) = CoordinateUseCase(cache)

    @Provides
    @Reusable
    fun provideFullRouteInformation(
        repository: FullRouteInformationRepository
    ) = FullRouteInformationUseCase(repository)

    @Provides
    @Reusable
    fun provideLocation(
        remote: LocationRepository
    ) = LocationUseCase(remote)

}
