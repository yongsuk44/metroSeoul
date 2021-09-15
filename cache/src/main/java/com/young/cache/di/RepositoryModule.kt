package com.young.cache.di

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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    @Reusable
    fun provideCacheSubwayRepository(
        dao: AllStationCodeDao
    ) : com.young.data.datasource.cache.CacheAllStationCodesDataSource = CacheAllStationCodesRepositoryImpl(dao)

    @Provides
    @Reusable
    fun provideCacheFullRouteInformationRepository(
        dao: FullRouteInformationDao ,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ) : com.young.data.datasource.cache.CacheFullRouteInformationDataSource = CacheFullRouteInformationRepositoryImpl(dao, dispatcher)

    @Provides
    @Reusable
    fun provideCacheStationCoordinatesRepository(
        dao: LocationDao
    ) : com.young.data.datasource.cache.CacheStationCoordinatesDataSource = CacheStationCoordinatesRepositoryImpl(dao)

}