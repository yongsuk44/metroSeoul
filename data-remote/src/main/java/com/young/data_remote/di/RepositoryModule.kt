package com.young.data_remote.di

import com.young.data_remote.api.PublicDataOpenApiService
import com.young.data_remote.api.PublicDataPortalApiService
import com.young.data_remote.api.SeoulApiService
import com.young.data_remote.api.TrailPorTalService
import com.young.data_remote.datasource.RemotePlatformEntranceDataSource
import com.young.data_remote.datasource.RemotePlatformEntranceDataSourceImpl
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
        publicDataPortalApiService: PublicDataOpenApiService
    ) : RemoteStationTelRepository =
        RemoteStationTelRepositoryImpl(publicDataPortalApiService)

    @Provides
    @Reusable
    fun provideTimeTableRepository(
        service: TrailPorTalService ,
        seoulApi : SeoulApiService
    ) : RemoteStationTimeDataSource =
        RemoteStationTimeTableRepositoryImpl(service , seoulApi)

    @Provides
    @Reusable
    fun provideAllRouteInformationRepository(
        seoul: SeoulApiService,
        service : TrailPorTalService
    ) : RemoteFullRouteInformationRepository =
        RemoteFullRouteInformationRepositoryImpl(seoul , service)

    @Provides
    @Reusable
    fun provideDetailInformationRepository(
        service : TrailPorTalService
    ) : RemotePlatformEntranceDataSource =
        RemotePlatFormEntranceRepositoryImpl(service)
}