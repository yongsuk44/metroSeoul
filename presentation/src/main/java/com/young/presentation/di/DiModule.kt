package com.young.presentation.di

import android.app.Application
import android.location.Geocoder
import com.young.presentation.consts.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DiModule {

    @Provides
    @Singleton
    fun provideResourceProvider(context: Application): ResourceProvider = ResourceProvider(context)
}