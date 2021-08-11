package com.young.domain.di

import com.young.domain.repository.location.LocalFullRouteInformationRepository
import com.young.domain.repository.location.LocalLocationRepository
import com.young.domain.repository.location.LocalStationCoordinatesRepository
import com.young.domain.repository.remote.RemoteFullRouteInformationRepository
import com.young.domain.repository.remote.RemoteLocationRepository
import com.young.domain.repository.remote.RemoteStationTelRepository
import com.young.domain.repository.remote.RemoteStationTimeTableRepository
import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import com.young.domain.repository.subwayfacilities.RemoteSubWayFacilitiesRepository
import com.young.domain.usecase.info.location.GetLocationUseCase
import com.young.domain.usecase.local.LocalGetFullRouteInformationUseCase
import com.young.domain.usecase.local.LocalGetStationDataUseCase
import com.young.domain.usecase.local.LocalInsertFullRouteInformationUseCase
import com.young.domain.usecase.local.LocalStationCoordinateUseCase
import com.young.domain.usecase.remote.*
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
    fun provideLocalStationCoordinate(
        local: LocalStationCoordinatesRepository
    ) = LocalStationCoordinateUseCase(local)

    @Provides
    @Reusable
    fun provideLocalLocation(
        local : LocalLocationRepository,
        remote: RemoteLocationRepository
    ) = GetLocationUseCase(remote , local)

    @Provides
    @Reusable
    fun provideSubwayFacilities(
        remote: RemoteSubWayFacilitiesRepository
    ) = RemoteGetSubWayFacilitiesDataUseCase(remote)

    @Provides
    @Reusable
    fun provideSubwayTel(
        remote: RemoteStationTelRepository
    ) = RemoteStationTelUseCase(remote)

    @Provides
    @Reusable
    fun provideInsertSubwayFacilities(
        local: LocalSubWayFacilitiesRepository
    ) = InsertSubWayFacilitiesDataUseCase(local)

    @Provides
    @Reusable
    fun provideGetTimeTable(
        remote: RemoteStationTimeTableRepository
    ): RemoteTimeTableUseCase = RemoteGetTimeTableUseCaseImpl(remote)


    @Provides
    @Reusable
    fun provideGetAllRouteInformation(
        remote: RemoteFullRouteInformationRepository
    ): RemoteFullRouteInformationUseCase = RemoteGetFullRouteInformationUseCase(remote)

    @Provides
    @Reusable
    fun provideUpdateSubwayFacilities(
        local: LocalSubWayFacilitiesRepository
    ) = UpdateSubWayFacilitiesDataUseCase(local)

    @Provides
    @Reusable
    fun provideGetDataTableSize(
        local: LocalSubWayFacilitiesRepository
    ) = GetSizeTableDataUseCase(local)

    @Provides
    @Reusable
    fun provideGetLocalAllData(
        local: LocalSubWayFacilitiesRepository
    ) = LocalGetSubWayFacilitiesDataUseCase(local)

    @Provides
    @Reusable
    fun provideInsertFullRouteInformation(
        local: LocalFullRouteInformationRepository
    ) = LocalInsertFullRouteInformationUseCase(local)

    @Provides
    @Reusable
    fun provideGetLocalFullRouteInformation(
        local: LocalFullRouteInformationRepository
    ) = LocalGetFullRouteInformationUseCase(local)

    @Provides
    @Reusable
    fun provideGetLocalStationData(
        local: LocalFullRouteInformationRepository
    ) = LocalGetStationDataUseCase(local)

}
