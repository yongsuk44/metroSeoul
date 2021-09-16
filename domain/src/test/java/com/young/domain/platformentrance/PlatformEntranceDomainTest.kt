package com.young.domain.platformentrance

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.young.domain.factory.DataFactory.getRandomString
import com.young.domain.factory.ModelFactory
import com.young.domain.fake.FakeCacheFullRouteInformationRepository
import com.young.domain.fake.FakeRemoteFullRouteInformationRepository
import com.young.domain.model.DomainPlatformEntrance
import com.young.domain.repository.location.CacheFullRouteInformationRepository
import com.young.domain.repository.remote.RemoteFullRouteInformationRepository
import com.young.domain.usecase.FullRouteInformationUseCase
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
class PlatformEntranceDomainTest {

    private lateinit var useCase : FullRouteInformationUseCase
    private lateinit var remote : RemoteFullRouteInformationRepository
    private lateinit var cache : CacheFullRouteInformationRepository

    var key: String = getRandomString()
    var railCode: String = getRandomString()
    var lineCd: String = getRandomString()
    var stinCode: String = getRandomString()

    @Before
    fun setUp() {
        remote = mock(FakeRemoteFullRouteInformationRepository::class.java)
        cache = mock(FakeCacheFullRouteInformationRepository::class.java)
        useCase = FullRouteInformationUseCase(remote, cache)
    }

    @Test
    fun `remote platform domain call`() {
        runBlocking {
            stubRemotePlatformEntranceDomain(ModelFactory.generatePlatformEntranceData())

            val remote = useCase.getPlatformEntranceData(key, railCode, lineCd, stinCode).singleOrNull()

            assert(remote is DomainPlatformEntrance)
        }
    }

    @Test
    fun `remote platform domain body null`() {
        runBlocking {
            stubRemotePlatformEntranceDomainNull(ModelFactory.generateNullPlatformEntranceData())

            val remote = useCase.getPlatformEntranceData(key, railCode, lineCd, stinCode).singleOrNull()

            assertNull(remote?.body)
        }
    }

    suspend fun stubRemotePlatformEntranceDomain(data : DomainPlatformEntrance) {
        whenever(remote.getPlatformEntranceData(key, railCode, lineCd, stinCode))
            .thenReturn(flowOf(data))
    }

    suspend fun stubRemotePlatformEntranceDomainNull(data : DomainPlatformEntrance) {
        whenever(remote.getPlatformEntranceData(key, railCode, lineCd, stinCode))
            .thenReturn(flowOf(data))
    }
}