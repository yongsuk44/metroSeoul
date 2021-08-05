package com.young.data_remote.di

import com.young.data_remote.api.PublicDataPortalApiService
import com.young.data_remote.api.SeoulApiService
import com.young.data_remote.api.TrailPorTalService
import com.young.data_remote.repository.*
import com.young.domain.repository.informationdetail.RemoteDetailInformationRepository
import com.young.domain.repository.information.remote.RemoteFullRouteInformationRepository
import com.young.domain.repository.information.remote.RemoteLocationRepository
import com.young.domain.repository.informationdetail.RemoteSubWayTelRepository
import com.young.domain.repository.informationdetail.RemoteTrailTimeTableRepository
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
        service: SeoulApiService
    ) : RemoteSubWayTelRepository =
        RemoteSubWayTelRepositoryImpl(service)

    @Provides
    @Reusable
    fun provideTimeTableRepository(
        service : TrailPorTalService
    ) : RemoteTrailTimeTableRepository =
        RemoteTrailTimeTableRepositoryImpl(service)

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