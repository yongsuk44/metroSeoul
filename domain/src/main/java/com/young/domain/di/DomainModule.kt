package com.young.domain.di

import com.young.domain.repository.location.CacheAllStationCodesRepository
import com.young.domain.repository.location.CacheFullRouteInformationRepository
import com.young.domain.repository.location.CacheStationCoordinatesRepository
import com.young.domain.repository.remote.RemoteFullRouteInformationRepository
import com.young.domain.repository.remote.RemoteLocationRepository
import com.young.domain.repository.remote.RemoteStationDataRepository
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
        cache: CacheFullRouteInformationRepository,
        remote: RemoteStationDataRepository
    ) = StationDataUseCase(cache, remote)

    @Provides
    @Reusable
    fun provideAllStationCodes(
        cache : CacheAllStationCodesRepository
    ) = AllStationCodeUseCase(cache)

    @Provides
    @Reusable
    fun provideCoordinate(
        cache : CacheStationCoordinatesRepository
    ) = CoordinateUseCase(cache)

    @Provides
    @Reusable
    fun provideFullRouteInformation(
        remote: RemoteFullRouteInformationRepository,
        cache: CacheFullRouteInformationRepository
    ) = FullRouteInformationUseCase(remote , cache)

    @Provides
    @Reusable
    fun provideLocation(
        remote: RemoteLocationRepository
    ) = LocationUseCase(remote)

}
