package com.young.data.di

import com.young.data.impl.cache.CacheFullRouteInformationDataSourceImpl
import com.young.data.impl.cache.CacheStationCoordinatesDataSourceImpl
import com.young.data.impl.remote.RemoteFullRouteInformationDataSourceImpl
import com.young.data.impl.remote.RemoteLocationDataSourceImpl
import com.young.data.impl.remote.RemoteStationDataSourceImpl
import com.young.domain.repository.location.CacheAllStationCodesRepository
import com.young.domain.repository.location.CacheFullRouteInformationRepository
import com.young.domain.repository.location.CacheStationCoordinatesRepository
import com.young.domain.repository.remote.RemoteFullRouteInformationRepository
import com.young.domain.repository.remote.RemoteLocationRepository
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
        datasource : com.young.data.datasource.remote.RemoteStationDataSource
    ) : RemoteStationDataRepository =
        RemoteStationDataSourceImpl(datasource)

    @Provides
    @Singleton
    fun provideRemoteFullRouteInformationDataSource(
        datasource : com.young.data.datasource.remote.RemoteFullRouteInformationDataSource
    ) : RemoteFullRouteInformationRepository =
        RemoteFullRouteInformationDataSourceImpl(datasource)

    @Provides
    @Singleton
    fun provideLocationDataSource(
        datasource : com.young.data.datasource.remote.RemoteLocationDataSource
    ) : RemoteLocationRepository =
        RemoteLocationDataSourceImpl(datasource)

    // cache
    @Provides
    @Singleton
    fun provideAllStationCodeDataSource(
        dataSource : com.young.data.datasource.cache.CacheAllStationCodesDataSource
    ) : CacheAllStationCodesRepository =
        com.young.data.impl.cache.CacheAllStationCodesDataSourceImpl(dataSource)

    @Provides
    @Singleton
    fun provideCacheFullRouteInformationDataSource(
        dataSource : com.young.data.datasource.cache.CacheFullRouteInformationDataSource
    ) : CacheFullRouteInformationRepository =
        CacheFullRouteInformationDataSourceImpl(dataSource)

    @Provides
    @Singleton
    fun provideCoordinatesDataSource(
        dataSource : com.young.data.datasource.cache.CacheStationCoordinatesDataSource
    ) : CacheStationCoordinatesRepository =
        CacheStationCoordinatesDataSourceImpl(dataSource)
}