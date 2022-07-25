package com.young.cache.di

import android.app.Application
import androidx.room.Room
import com.young.cache.AppDataBase
import com.young.cache.dao.AllStationCodeDao
import com.young.cache.dao.FullRouteInformationDao
import com.young.cache.dao.LocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
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