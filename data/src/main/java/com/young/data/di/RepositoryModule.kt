package com.young.data.di

import com.young.data.dao.AllStationCodeDao
import com.young.data.dao.FullRouteInformationDao
import com.young.data.dao.LocationDao
import com.young.data.dao.SubWayFacilitiesDao
import com.young.data.repository.*
import com.young.domain.repository.location.LocalAllStationCodesRepository
import com.young.domain.repository.location.LocalFullRouteInformationRepository
import com.young.domain.repository.location.LocalLocationRepository
import com.young.domain.repository.location.LocalStationCoordinatesRepository
import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
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
    fun provideLocalSubwayRepository(
        dao: SubWayFacilitiesDao
    ) : LocalSubWayFacilitiesRepository = LocalSubWayFacilitiesRepositoryImpl(dao)

    @Provides
    @Reusable
    fun provideLocalFullRouteInformationRepository(
        dao: FullRouteInformationDao
    ) : LocalFullRouteInformationRepository = LocalFullRouteInformationRepositoryImpl(dao)

    @Provides
    @Reusable
    fun provideLocalLocationRepository(
        dao: FullRouteInformationDao
    ) : LocalLocationRepository = LocalLocationRepositoryImpl(dao)

    @Provides
    @Reusable
    fun provideLocalStationCoordinatesRepository(
        dao: LocationDao
    ) : LocalStationCoordinatesRepository = LocalStationCoordinatesRepositoryImpl(dao)

    @Provides
    @Reusable
    fun provideAllStationCodesRepository(
        dao: AllStationCodeDao
    ) : LocalAllStationCodesRepository = LocalAllStationCodesRepositoryImpl(dao)

}