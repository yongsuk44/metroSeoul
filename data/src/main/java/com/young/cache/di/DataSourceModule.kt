package com.young.cache.di

import com.young.cache.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.cache.datasource.remote.RemoteStationDataSource
import com.young.cache.impl.remote.RemoteFullRouteInformationDataSourceImpl
import com.young.cache.impl.remote.RemoteStationDataSourceImpl
import com.young.domain.repository.remote.RemoteFullRouteInformationRepository
import com.young.domain.repository.remote.RemoteStationDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideStationDataSource(
        datasource : RemoteStationDataSource
    ) : RemoteStationDataRepository =
        RemoteStationDataSourceImpl(datasource)

    @Provides
    @Singleton
    fun provideFullRouteInformationDataSource(
        datasource : RemoteFullRouteInformationDataSource
    ) : RemoteFullRouteInformationRepository =
        RemoteFullRouteInformationDataSourceImpl(datasource)
}