package com.young.cache.di

import com.young.cache.dao.AllStationCodeDao
import com.young.cache.dao.FullRouteInformationDao
import com.young.cache.dao.LocationDao
import com.young.cache.dao.SubWayFacilitiesDao
import com.young.cache.repository.*
import com.young.domain.repository.location.CacheAllStationCodesRepository
import com.young.domain.repository.location.CacheFullRouteInformationRepository
import com.young.domain.repository.location.CacheLocationRepository
import com.young.domain.repository.location.CacheStationCoordinatesRepository
import com.young.domain.repository.subwayfacilities.CacheSubWayFacilitiesRepository
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
        dao: SubWayFacilitiesDao
    ) : CacheSubWayFacilitiesRepository = CacheSubWayFacilitiesRepositoryImpl(dao)

    @Provides
    @Reusable
    fun provideCacheFullRouteInformationRepository(
        dao: FullRouteInformationDao
    ) : CacheFullRouteInformationRepository = CacheFullRouteInformationRepositoryImpl(dao)

    @Provides
    @Reusable
    fun provideCacheLocationRepository(
        dao: FullRouteInformationDao
    ) : CacheLocationRepository = CacheLocationRepositoryImpl(dao)

    @Provides
    @Reusable
    fun provideCacheStationCoordinatesRepository(
        dao: LocationDao
    ) : CacheStationCoordinatesRepository = CacheStationCoordinatesRepositoryImpl(dao)

    @Provides
    @Reusable
    fun provideAllStationCodesRepository(
        dao: AllStationCodeDao
    ) : CacheAllStationCodesRepository = CacheAllStationCodesRepositoryImpl(dao)

}