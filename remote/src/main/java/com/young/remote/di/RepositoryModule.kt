package com.young.remote.di

import com.young.cache.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.cache.datasource.remote.RemoteStationDataSource
import com.young.remote.api.PublicDataOpenApiService
import com.young.remote.api.PublicDataPortalApiService
import com.young.remote.api.SeoulApiService
import com.young.remote.api.TrailPorTalService
import com.young.remote.repository.*
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
        publicDataPortalApiService: PublicDataOpenApiService
    ) : RemoteStationDataSource =
        RemoteStationDataRepositoryImpl(publicDataPortalApiService)

    @Provides
    @Reusable
    fun provideTimeTableRepository(
        service: TrailPorTalService ,
        seoulApi : SeoulApiService
    ) : RemoteStationTimeDataSource =
        RemoteStationTimeTableRepositoryImpl(service , seoulApi)

    @Provides
    @Reusable
    fun provideDetailInformationRepository(
        seoulApiService: SeoulApiService,
        service: TrailPorTalService
    ) : RemoteFullRouteInformationDataSource =
        RemoteFullRouteInformationRepositoryImpl(seoulApiService ,service)
}