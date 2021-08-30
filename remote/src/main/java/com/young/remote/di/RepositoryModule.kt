package com.young.remote.di

import com.young.cache.cache.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.cache.cache.datasource.remote.RemoteLocationDataSource
import com.young.cache.cache.datasource.remote.RemoteStationDataSource
import com.young.remote.api.PublicDataOpenApiService
import com.young.remote.api.SeoulApiService
import com.young.remote.api.TrailPorTalService
import com.young.remote.repository.RemoteFullRouteInformationRepositoryImpl
import com.young.remote.repository.RemoteLocationRepositoryImpl
import com.young.remote.repository.RemoteStationDataRepositoryImpl
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
    fun provideSubWayTelRepository(
        publicDataPortalApiService: PublicDataOpenApiService ,
        service : TrailPorTalService ,
        seoulApi: SeoulApiService
    ) : RemoteStationDataSource =
        RemoteStationDataRepositoryImpl(publicDataPortalApiService,service, seoulApi)

    @Provides
    @Reusable
    fun provideDetailInformationRepository(
        seoulApiService: SeoulApiService,
        service: TrailPorTalService
    ) : RemoteFullRouteInformationDataSource =
        RemoteFullRouteInformationRepositoryImpl(seoulApiService ,service)

    @Provides
    @Reusable
    fun provideLocationRepository(
        api : TrailPorTalService
    ) : RemoteLocationDataSource =
        RemoteLocationRepositoryImpl(api)
}