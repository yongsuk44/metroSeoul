package com.young.cache.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import com.young.cache.datastore.LocationSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import com.young.cache.Metro.LocationStore
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun providesLocationDataStore(@ApplicationContext context: Context) : DataStore<LocationStore> =
        DataStoreFactory.create(
            serializer = LocationSerializer,
            produceFile = { context.dataStoreFile("metro.proto") }
        )
}