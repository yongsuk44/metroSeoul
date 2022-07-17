package com.young.presentation.di

import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(ApplicationComponent::class)
@Module
object CoroutineDispatcherModule {

    @Provides
    @Reusable
    @IoDispatcher
    fun provideIoDispatchers() : CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Reusable
    @DefaultDispatcher
    fun provideDefaultDispatchers() : CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Reusable
    @MainDispatcher
    fun provideMainDispatchers() : CoroutineDispatcher = Dispatchers.Main
}