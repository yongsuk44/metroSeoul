package com.young.data_remote.di

import com.young.data_remote.datasource.RemotePlatformEntranceDataSource
import com.young.data_remote.datasource.RemotePlatformEntranceDataSourceImpl
import com.young.data_remote.datasource.RemoteStationTimeDataSource
import com.young.data_remote.datasource.RemoteStationTimeDataSourceImpl
import com.young.domain.repository.remote.RemotePlatformEntranceRepository
import com.young.domain.repository.remote.RemoteStationTimeTableRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteStationTimeDataSource(
        datasource : RemoteStationTimeDataSource
    ) : RemoteStationTimeTableRepository =
        RemoteStationTimeDataSourceImpl(datasource)

    @Provides
    @Singleton
    fun provideRemotePlatformEntranceDAtaSource(
        datasource : RemotePlatformEntranceDataSource
    ) : RemotePlatformEntranceRepository =
        RemotePlatformEntranceDataSourceImpl(datasource)
}