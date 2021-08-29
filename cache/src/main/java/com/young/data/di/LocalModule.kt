package com.young.data.di

import android.app.Application
import androidx.room.Room
import com.young.data.AppDataBase
import com.young.data.dao.AllStationCodeDao
import com.young.data.dao.FullRouteInformationDao
import com.young.data.dao.LocationDao
import com.young.data.dao.SubWayFacilitiesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class CacheModule {

    @Provides
    @Singleton
    fun provideRoomInstance(context : Application) : AppDataBase {
        return Room.databaseBuilder(context , AppDataBase::class.java , "metroDB")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideSubWayFacilitiesDao(database : AppDataBase) : SubWayFacilitiesDao =
        database.subWayFacilitiesDao()

    @Provides
    @Singleton
    fun provideFullRouteInformationDao(database : AppDataBase) : FullRouteInformationDao =
        database.fullRouteInformationDao()

    @Provides
    @Singleton
    fun provideLocationDao(database : AppDataBase) : LocationDao =
        database.locationDao()

    @Provides
    @Singleton
    fun provideAllCodesDao(database : AppDataBase) : AllStationCodeDao =
        database.allStationCodeDao()

}