package com.young.data_remote.di

import com.young.data_remote.api.SubwayFacilitiesService
import com.young.data_remote.repository.RemoteSubWayFacilitiesRepositoryImpl
import com.young.domain.repository.subwayfacilities.RemoteSubWayFacilitiesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideSubWayFacilitiesRepository(
        subwayFacilitiesService: SubwayFacilitiesService
    ) : RemoteSubWayFacilitiesRepository =
        RemoteSubWayFacilitiesRepositoryImpl(subwayFacilitiesService)
}