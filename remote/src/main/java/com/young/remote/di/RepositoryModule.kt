package com.young.remote.di

import com.young.base.di.IoDispatcher
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
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    @Reusable
    fun provideSubWayTelRepository(
        publicDataPortalApiService: PublicDataOpenApiService ,
        service : TrailPorTalService ,
        seoulApi: SeoulApiService,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ) : com.young.data.datasource.remote.RemoteStationDataSource =
        RemoteStationDataRepositoryImpl(publicDataPortalApiService,service, seoulApi, dispatcher)

    @Provides
    @Reusable
    fun provideDetailInformationRepository(
        seoulApiService: SeoulApiService,
        service: TrailPorTalService,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ) : com.young.data.datasource.remote.RemoteFullRouteInformationDataSource =
        RemoteFullRouteInformationRepositoryImpl(seoulApiService ,service, dispatcher)

    @Provides
    @Reusable
    fun provideLocationRepository(
        api : TrailPorTalService,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ) : com.young.data.datasource.remote.RemoteLocationDataSource =
        RemoteLocationRepositoryImpl(api, dispatcher)
}