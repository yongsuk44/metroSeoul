package com.young.cache.cache.di

import com.young.cache.cache.datasource.cache.CacheAllStationCodesDataSource
import com.young.cache.cache.datasource.cache.CacheFullRouteInformationDataSource
import com.young.cache.cache.datasource.cache.CacheStationCoordinatesDataSource
import com.young.cache.cache.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.cache.cache.datasource.remote.RemoteLocationDataSource
import com.young.cache.cache.datasource.remote.RemoteStationDataSource
import com.young.cache.cache.impl.cache.CacheAllStationCodesDataSourceImpl
import com.young.cache.cache.impl.cache.CacheFullRouteInformationDataSourceImpl
import com.young.cache.cache.impl.cache.CacheStationCoordinatesDataSourceImpl
import com.young.cache.cache.impl.remote.RemoteFullRouteInformationDataSourceImpl
import com.young.cache.cache.impl.remote.RemoteLocationDataSourceImpl
import com.young.cache.cache.impl.remote.RemoteStationDataSourceImpl
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
        datasource : RemoteStationDataSource
    ) : RemoteStationDataRepository =
        RemoteStationDataSourceImpl(datasource)

    @Provides
    @Singleton
    fun provideRemoteFullRouteInformationDataSource(
        datasource : RemoteFullRouteInformationDataSource
    ) : RemoteFullRouteInformationRepository =
        RemoteFullRouteInformationDataSourceImpl(datasource)

    @Provides
    @Singleton
    fun provideLocationDataSource(
        datasource : RemoteLocationDataSource
    ) : RemoteLocationRepository =
        RemoteLocationDataSourceImpl(datasource)

    // cache
    @Provides
    @Singleton
    fun provideAllStationCodeDataSource(
        dataSource : CacheAllStationCodesDataSource
    ) : CacheAllStationCodesRepository =
        CacheAllStationCodesDataSourceImpl(dataSource)

    @Provides
    @Singleton
    fun provideCacheFullRouteInformationDataSource(
        dataSource : CacheFullRouteInformationDataSource
    ) : CacheFullRouteInformationRepository =
        CacheFullRouteInformationDataSourceImpl(dataSource)

    @Provides
    @Singleton
    fun provideCoordinatesDataSource(
        dataSource : CacheStationCoordinatesDataSource
    ) : CacheStationCoordinatesRepository =
        CacheStationCoordinatesDataSourceImpl(dataSource)
}