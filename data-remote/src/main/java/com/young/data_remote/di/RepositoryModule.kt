package com.young.data_remote.di

import com.young.data_remote.api.SubWayTelService
import com.young.data_remote.api.SubwayFacilitiesService
import com.young.data_remote.repository.RemoteSubWayFacilitiesRepositoryImpl
import com.young.data_remote.repository.RemoteSubWayTelRepositoryImpl
import com.young.domain.repository.subwayfacilities.RemoteSubWayFacilitiesRepository
import com.young.domain.repository.subwaytel.RemoteSubWayTelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideSubWayFacilitiesRepository(
        subwayFacilitiesService: SubwayFacilitiesService
    ) : RemoteSubWayFacilitiesRepository =
        RemoteSubWayFacilitiesRepositoryImpl(subwayFacilitiesService)

    @Provides
    @Singleton
    fun provideSubWayTelRepository(
        service: SubWayTelService
    ) : RemoteSubWayTelRepository =
        RemoteSubWayTelRepositoryImpl(service)
}