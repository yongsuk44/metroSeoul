package com.young.data.platform

import com.nhaarman.mockito_kotlin.whenever
import com.young.data.DataFactory.getRandomString
import com.young.data.ModelFactory
import com.young.data.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.data.impl.remote.RemoteFullRouteInformationDataSourceImpl
import com.young.data.model.DataStationEntrance
import com.young.domain.model.DomainStationEntrance
import junit.framework.Assert.assertNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class StationEntranceDataTest {

    private lateinit var remoteFullRouteInformationDataSourceImpl: RemoteFullRouteInformationDataSourceImpl
    private lateinit var remoteFullRouteInformationDataSource : RemoteFullRouteInformationDataSource

    var key: String = getRandomString()
    var railCode: String = getRandomString()
    var lineCd: String = getRandomString()
    var stinCode: String = getRandomString()

    @Before
    fun setUp() {
        remoteFullRouteInformationDataSource = mock(FakeRemoteFullRouteInformationDataSource::class.java)
        remoteFullRouteInformationDataSourceImpl = RemoteFullRouteInformationDataSourceImpl(remoteFullRouteInformationDataSource)
    }

    @Test
    fun `platform data to domain check`() {
        runBlocking {
            stubPlatformData(ModelFactory.generateStationEntranceData())

            val impl = remoteFullRouteInformationDataSourceImpl.getStationEntranceData(key, railCode, lineCd, stinCode).singleOrNull()

            assert(impl is DomainStationEntrance)
        }
    }

    @Test
    fun `platform body is null Check`() {
        runBlocking {
            stubNullPlatformData(ModelFactory.generateNullStationEntranceData())

            val impl = remoteFullRouteInformationDataSourceImpl.getStationEntranceData(key, railCode, lineCd, stinCode).singleOrNull()

            assertNull(impl?.body)
        }
    }

    suspend fun stubPlatformData(data : DataStationEntrance) {
        whenever(remoteFullRouteInformationDataSource.getStationEntranceData(key, railCode, lineCd, stinCode))
            .thenReturn(flowOf(data))
    }

    suspend fun stubNullPlatformData(data : DataStationEntrance) {
        whenever(remoteFullRouteInformationDataSource.getStationEntranceData(key, railCode, lineCd, stinCode))
            .thenReturn(flowOf(data))
    }
}