package com.young.remote.di

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
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @Reusable
    fun provideSubWayTelRepository(
        publicDataPortalApiService: PublicDataOpenApiService ,
        service : TrailPorTalService ,
        seoulApi: SeoulApiService
    ) : com.young.data.datasource.remote.RemoteStationDataSource =
        RemoteStationDataRepositoryImpl(publicDataPortalApiService,service, seoulApi)

    @Provides
    @Reusable
    fun provideDetailInformationRepository(
        seoulApiService: SeoulApiService,
        service: TrailPorTalService
    ) : com.young.data.datasource.remote.RemoteFullRouteInformationDataSource =
        RemoteFullRouteInformationRepositoryImpl(seoulApiService ,service)

    @Provides
    @Reusable
    fun provideLocationRepository(
        api : TrailPorTalService
    ) : com.young.data.datasource.remote.RemoteLocationDataSource =
        RemoteLocationRepositoryImpl(api)
}