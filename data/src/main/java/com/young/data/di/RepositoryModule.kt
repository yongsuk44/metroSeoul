package com.young.data.di

import com.young.data.dao.FullRouteInformationDao
import com.young.data.dao.LocationDao
import com.young.data.dao.SubWayFacilitiesDao
import com.young.data.dao.TelDao
import com.young.data.repository.*
import com.young.domain.repository.information.local.LocalFullRouteInformationRepository
import com.young.domain.repository.information.local.LocalLocationRepository
import com.young.domain.repository.information.local.LocalStationCoordinatesRepository
import com.young.domain.repository.informationdetail.local.LocalStationTelRepository
import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

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
    fun provideLocalStationTelRepository(
        dao: TelDao
    ) : LocalStationTelRepository = LocalStationTelRepositoryImpl(dao)

}