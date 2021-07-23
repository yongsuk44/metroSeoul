package com.young.data_remote.di

import com.young.data_remote.api.PublicDataPortalApiService
import com.young.data_remote.api.SeoulApiService
import com.young.data_remote.api.TrailPorTalService
import com.young.data_remote.repository.RemoteFullRouteInformationRepositoryImpl
import com.young.data_remote.repository.RemoteSubWayFacilitiesRepositoryImpl
import com.young.data_remote.repository.RemoteSubWayTelRepositoryImpl
import com.young.data_remote.repository.RemoteTrailTimeTableRepositoryImpl
import com.young.domain.repository.information.remote.RemoteFullRouteInformationRepository
import com.young.domain.repository.information.remote.RemoteSubWayTelRepository
import com.young.domain.repository.information.remote.RemoteTrailTimeTableRepository
import com.young.domain.repository.subwayfacilities.RemoteSubWayFacilitiesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideSubWayFacilitiesRepository(
        subwayFacilitiesService: PublicDataPortalApiService
    ) : RemoteSubWayFacilitiesRepository =
        RemoteSubWayFacilitiesRepositoryImpl(subwayFacilitiesService)

    @Provides
    @Singleton
    fun provideSubWayTelRepository(
        service: SeoulApiService
    ) : RemoteSubWayTelRepository =
        RemoteSubWayTelRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideTimeTableRepository(
        service : TrailPorTalService
    ) : RemoteTrailTimeTableRepository =
        RemoteTrailTimeTableRepositoryImpl(service)

    @Provides
    @Singleton
    fun provideAllRouteInformationRepository(
        service : TrailPorTalService
    ) : RemoteFullRouteInformationRepository =
        RemoteFullRouteInformationRepositoryImpl(service)
}