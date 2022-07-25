package com.young.cache.di

import androidx.datastore.core.DataStore
import com.young.cache.Metro.LocationStore
import com.young.cache.dao.AllStationCodeDao
import com.young.cache.dao.FullRouteInformationDao
import com.young.cache.dao.LocationDao
import com.young.cache.repository.CacheAllStationCodesRepositoryImpl
import com.young.cache.repository.CacheFullRouteInformationRepositoryImpl
import com.young.cache.repository.CacheLocationRepositoryImpl
import com.young.cache.repository.CacheStationCoordinatesRepositoryImpl
import com.young.data.datasource.cache.CacheLocationDataSource
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {

    @Provides
    @Reusable
    fun provideCacheSubwayRepository(
        dao: AllStationCodeDao
    ) : com.young.data.datasource.cache.CacheAllStationCodesDataSource = CacheAllStationCodesRepositoryImpl(dao)

    @Provides
    @Reusable
    fun provideCacheFullRouteInformationRepository(
        dao: FullRouteInformationDao
    ) : com.young.data.datasource.cache.CacheFullRouteInformationDataSource = CacheFullRouteInformationRepositoryImpl(dao)

    @Provides
    @Reusable
    fun provideCacheStationCoordinatesRepository(
        dao: LocationDao
    ) : com.young.data.datasource.cache.CacheStationCoordinatesDataSource = CacheStationCoordinatesRepositoryImpl(dao)


    @Provides
    @Reusable
    fun provideCacheLocationRepository(
        locationDataStore: DataStore<LocationStore>
    ) : CacheLocationDataSource = CacheLocationRepositoryImpl(locationDataStore)
}