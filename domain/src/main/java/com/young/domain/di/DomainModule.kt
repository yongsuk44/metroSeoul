package com.young.domain.di

import com.young.domain.repository.subwayfacilities.RemoteSubWayFacilitiesRepository
import com.young.domain.usecase.BaseUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import com.young.domain.usecase.subwayfacilities.GetSubWayFacilitiesDataUseCase
import dagger.Binds
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Reusable
    fun provideSubwayFacilities(
        remote : RemoteSubWayFacilitiesRepository
    ) = GetSubWayFacilitiesDataUseCase(remote)
}