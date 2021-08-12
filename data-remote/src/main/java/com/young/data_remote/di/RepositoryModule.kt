package com.young.data_remote.di

import com.young.data_remote.api.PublicDataOpenApiService
import com.young.data_remote.api.PublicDataPortalApiService
import com.young.data_remote.api.SeoulApiService
import com.young.data_remote.api.TrailPorTalService
import com.young.data_remote.datasource.RemoteStationTimeDataSource
import com.young.data_remote.repository.*
import com.young.domain.repository.remote.*
import com.young.domain.repository.subwayfacilities.RemoteSubWayFacilitiesRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    @Reusable
    fun provideLocationRepository(
        service : TrailPorTalService
    ) : RemoteLocationRepository =
        RemoteLocationRepositoryImpl(service)

    @Provides
    @Reusable
    fun provideSubWayFacilitiesRepository(
        subwayFacilitiesService: PublicDataPortalApiService
    ) : RemoteSubWayFacilitiesRepository =
        RemoteSubWayFacilitiesRepositoryImpl(subwayFacilitiesService)

    @Provides
    @Reusable
    fun provideSubWayTelRepository(
        service: SeoulApiService,
        publicDataPortalApiService: PublicDataOpenApiService
    ) : RemoteStationTelRepository =
        RemoteStationTelRepositoryImpl(service ,publicDataPortalApiService)

    @Provides
    @Reusable
    fun provideTimeTableRepository(
        service: TrailPorTalService
    ) : RemoteStationTimeDataSource =
        RemoteStationTimeTableRepositoryImpl(service)

    @Provides
    @Reusable
    fun provideAllRouteInformationRepository(
        service : TrailPorTalService
    ) : RemoteFullRouteInformationRepository =
        RemoteFullRouteInformationRepositoryImpl(service)

    @Provides
    @Reusable
    fun provideDetailInformationRepository(
        service : TrailPorTalService
    ) : RemoteDetailInformationRepository =
        RemoteDetailInformationRepositoryImpl(service)
}