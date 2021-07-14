package com.young.data.di

import android.app.Application
import androidx.room.Room
import com.young.data.AppDataBase
import com.young.data.dao.SubWayFacilitiesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    @Provides
    @Singleton
    fun provideRoomInstance(context : Application) : AppDataBase {
        return Room.databaseBuilder(context , AppDataBase::class.java , "metroDB").build()
    }

    @Provides
    @Singleton
    fun provideSubWayFacilitiesDao(database : AppDataBase) : SubWayFacilitiesDao {
        return database.subWayFacilitiesDao()
    }
}