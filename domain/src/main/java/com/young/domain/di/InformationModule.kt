package com.young.domain.di

import com.young.domain.usecase.FullRouteInformationBaseUseCase
import com.young.domain.usecase.FullRouteInformationUseCase
import com.young.domain.usecase.information.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class InformationModule {

    @Binds
    abstract fun provideFullRouteInformation(fullRouteInformationUseCase: FullRouteInformationUseCase): FullRouteInformationBaseUseCase

    @Binds
    abstract fun provideFindStationRouteInformation(findStationRouteInformationUseCase: FindStationRouteInformationUseCase): FindStationRouteInformationBaseUseCase

    @Binds
    abstract fun provideInsertRouteInformation(insertRouteInformationUseCase: InsertRouteInformationUseCase): InsertRouteInformationBaseUseCase

    @Binds
    abstract fun provideInsertStationCode(insertStationCodeUseCase: InsertStationCodeUseCase): InsertStationCodeBaseUseCase

    @Binds
    abstract fun provideReadRouteInformation(readRouteInformationUseCase: ReadRouteInformationUseCase): ReadRouteInformationBaseUseCase

    @Binds
    abstract fun provideReadStationTelNumber(readStationTelNumberUseCase: ReadStationTelNumberUseCase): ReadStationTelNumberBaseUseCase

}