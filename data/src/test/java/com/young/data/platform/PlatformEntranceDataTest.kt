package com.young.data.platform

import com.nhaarman.mockito_kotlin.whenever
import com.young.data.DataFactory.getRandomString
import com.young.data.ModelFactory
import com.young.data.datasource.cache.CacheFullRouteInformationDataSource
import com.young.data.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.data.impl.FullRouteInformationDataSourceImpl
import com.young.data.model.DataStationEntrance
import com.young.domain.model.DomainStationEntrance
import junit.framework.Assert.assertNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class StationEntranceDataTest {

    private lateinit var fullRouteInformationDataSourceImpl: FullRouteInformationDataSourceImpl
    private lateinit var remote: RemoteFullRouteInformationDataSource
    private lateinit var cache: CacheFullRouteInformationDataSource

    var key: String = getRandomString()
    var railCode: String = getRandomString()
    var lineCd: String = getRandomString()
    var stinCode: String = getRandomString()

    @Before
    fun setUp() {
        remote = mock(RemoteFullRouteInformationDataSource::class.java)
        cache = mock(CacheFullRouteInformationDataSource::class.java)
        fullRouteInformationDataSourceImpl = FullRouteInformationDataSourceImpl(cache, remote)
    }

    @Test
    fun `platform data to domain check`() {
        runBlocking {
            stubPlatformData(ModelFactory.generateStationEntranceData())

            val impl = fullRouteInformationDataSourceImpl.getStationEntranceData(key, railCode, lineCd, stinCode).singleOrNull()

            assert(impl is DomainStationEntrance)
        }
    }

    @Test
    fun `platform body is null Check`() {
        runBlocking {
            stubNullPlatformData(ModelFactory.generateNullStationEntranceData())

            val impl = fullRouteInformationDataSourceImpl.getStationEntranceData(key, railCode, lineCd, stinCode).singleOrNull()

            assertNull(impl?.body)
        }
    }

    suspend fun stubPlatformData(data: DataStationEntrance) {
        whenever(remote.getStationEntranceData(key, railCode, lineCd, stinCode))
            .thenReturn(flowOf(data))
    }

    suspend fun stubNullPlatformData(data: DataStationEntrance) {
        whenever(remote.getStationEntranceData(key, railCode, lineCd, stinCode))
            .thenReturn(flowOf(data))
    }
}