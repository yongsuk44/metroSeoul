package com.young.data.di

import com.young.data.datasource.cache.CacheFullRouteInformationDataSource
import com.young.data.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.data.impl.*
import com.young.domain.repository.*
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
        datasource: com.young.data.datasource.remote.RemoteStationDataSource
    ): StationDataRepository =
        StationDataSourceImpl(datasource)

    @Provides
    @Singleton
    fun provideFullRouteInformationDataSource(
        remote: RemoteFullRouteInformationDataSource,
        cache: CacheFullRouteInformationDataSource
    ): FullRouteInformationRepository =
        FullRouteInformationDataSourceImpl(cache, remote)

    @Provides
    @Singleton
    fun provideLocationDataSource(
        remote: com.young.data.datasource.remote.RemoteLocationDataSource
    ): LocationRepository =
        LocationDataSourceImpl(remote)

    // cache
    @Provides
    @Singleton
    fun provideAllStationCodeDataSource(
        cache: com.young.data.datasource.cache.CacheAllStationCodesDataSource
    ): AllStationCodesRepository =
        AllStationCodesDataSourceImpl(cache)

    @Provides
    @Singleton
    fun provideCoordinatesDataSource(
        cache: com.young.data.datasource.cache.CacheStationCoordinatesDataSource
    ): StationCoordinatesRepository =
        CoordinatesDataSourceImpl(cache)
}