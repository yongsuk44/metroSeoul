package com.young.domain.di

import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import com.young.domain.repository.subwayfacilities.RemoteSubWayFacilitiesRepository
import com.young.domain.usecase.BaseUseCase
import com.young.domain.usecase.subwayfacilities.local.GetSizeTableDataUseCase
import com.young.domain.usecase.subwayfacilities.local.InsertSubWayFacilitiesDataUseCase
import com.young.domain.usecase.subwayfacilities.local.LocalGetSubWayFacilitiesDataUseCase
import com.young.domain.usecase.subwayfacilities.local.UpdateSubWayFacilitiesDataUseCase
import com.young.domain.usecase.subwayfacilities.remote.RemoteGetSubWayFacilitiesDataUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.Binds
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DomainSubWayFacilitiesModule {

    @Provides
    @Reusable
    fun provideSubwayFacilities(
        remote : RemoteSubWayFacilitiesRepository
    ) = RemoteGetSubWayFacilitiesDataUseCase(remote)

    @Provides
    @Reusable
    fun provideInsertSubwayFacilities(
        local : LocalSubWayFacilitiesRepository
    ) = InsertSubWayFacilitiesDataUseCase(local)

    @Provides
    @Reusable
    fun provideUpdateSubwayFacilities(
        local : LocalSubWayFacilitiesRepository
    ) = UpdateSubWayFacilitiesDataUseCase(local)

    @Provides
    @Reusable
    fun provideGetDataTableSize(
        local: LocalSubWayFacilitiesRepository
    ) = GetSizeTableDataUseCase(local)

    @Provides
    @Reusable
    fun provideGetLocalAllData(
        local : LocalSubWayFacilitiesRepository
    ) = LocalGetSubWayFacilitiesDataUseCase(local)
}
