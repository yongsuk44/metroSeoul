package com.young.data.di

import android.app.Application
import androidx.room.Room
import com.young.data.AppDataBase
import com.young.data.dao.FullRouteInformationDao
import com.young.data.dao.SubWayFacilitiesDao
import com.young.data.repository.LocalSubWayFacilitiesRepositoryImpl
import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun provideRoomInstance(context : Application) : AppDataBase {
        return Room.databaseBuilder(context , AppDataBase::class.java , "metroDB").build()
    }

    @Provides
    @Singleton
    fun provideSubWayFacilitiesDao(database : AppDataBase) : SubWayFacilitiesDao =
        database.subWayFacilitiesDao()

    @Provides
    @Singleton
    fun provideFullRouteInformationDao(database : AppDataBase) : FullRouteInformationDao =
        database.fullRouteInformationDao()


}