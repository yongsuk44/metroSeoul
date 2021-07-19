package com.young.domain.di

import com.young.domain.repository.information.RemoteFullRouteInformationRepository
import com.young.domain.repository.information.RemoteSubWayTelRepository
import com.young.domain.repository.information.RemoteTrailTimeTableRepository
import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import com.young.domain.repository.subwayfacilities.RemoteSubWayFacilitiesRepository
import com.young.domain.usecase.information.remote.*
import com.young.domain.usecase.subwayfacilities.local.GetSizeTableDataUseCase
import com.young.domain.usecase.subwayfacilities.local.InsertSubWayFacilitiesDataUseCase
import com.young.domain.usecase.subwayfacilities.local.LocalGetSubWayFacilitiesDataUseCase
import com.young.domain.usecase.subwayfacilities.local.UpdateSubWayFacilitiesDataUseCase
import com.young.domain.usecase.subwayfacilities.remote.RemoteGetSubWayFacilitiesDataUseCase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

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
    fun provideSubwayTel(
        remote : RemoteSubWayTelRepository
    ) = RemoteGetSubWayTelUseCase(remote)

    @Provides
    @Reusable
    fun provideInsertSubwayFacilities(
        local : LocalSubWayFacilitiesRepository
    ) = InsertSubWayFacilitiesDataUseCase(local)

    @Provides
    @Reusable
    fun provideGetTimeTable(
        remote : RemoteTrailTimeTableRepository
    ) : RemoteTimeTableUseCase = RemoteGetTimeTableUseCaseImpl(remote)


    @Provides
    @Reusable
    fun provideGetAllRouteInformation(
        remote : RemoteFullRouteInformationRepository
    ) : RemoteFullRouteInformationUseCase = RemoteGetFullRouteInformationUseCase(remote)

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
