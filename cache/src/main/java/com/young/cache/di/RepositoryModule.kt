package com.young.cache.di

import com.young.cache.cache.datasource.cache.CacheAllStationCodesDataSource
import com.young.cache.cache.datasource.cache.CacheFullRouteInformationDataSource
import com.young.cache.cache.datasource.cache.CacheStationCoordinatesDataSource
import com.young.cache.dao.AllStationCodeDao
import com.young.cache.dao.FullRouteInformationDao
import com.young.cache.dao.LocationDao
import com.young.cache.repository.CacheAllStationCodesRepositoryImpl
import com.young.cache.repository.CacheFullRouteInformationRepositoryImpl
import com.young.cache.repository.CacheStationCoordinatesRepositoryImpl
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
    fun provideCacheSubwayRepository(
        dao: AllStationCodeDao
    ) : CacheAllStationCodesDataSource = CacheAllStationCodesRepositoryImpl(dao)

    @Provides
    @Reusable
    fun provideCacheFullRouteInformationRepository(
        dao: FullRouteInformationDao
    ) : CacheFullRouteInformationDataSource = CacheFullRouteInformationRepositoryImpl(dao)

    @Provides
    @Reusable
    fun provideCacheStationCoordinatesRepository(
        dao: LocationDao
    ) : CacheStationCoordinatesDataSource = CacheStationCoordinatesRepositoryImpl(dao)

}