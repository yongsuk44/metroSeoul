package com.young.data.di

import com.young.data.dao.SubWayFacilitiesDao
import com.young.data.repository.LocalSubWayFacilitiesRepositoryImpl
import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocalSubwayRepository(
        dao: SubWayFacilitiesDao
    ) : LocalSubWayFacilitiesRepository = LocalSubWayFacilitiesRepositoryImpl(dao)

}