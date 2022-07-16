package com.young.domain.StationEntrance

import com.nhaarman.mockito_kotlin.whenever
import com.young.domain.factory.DataFactory.getRandomString
import com.young.domain.factory.ModelFactory
import com.young.domain.model.DomainStationEntrance
import com.young.domain.repository.FullRouteInformationRepository
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
class StationEntranceDomainTest {

    private lateinit var useCase: FullRouteInformationUseCase
    private lateinit var repository: FullRouteInformationRepository

    var key: String = getRandomString()
    var railCode: String = getRandomString()
    var lineCd: String = getRandomString()
    var stinCode: String = getRandomString()

    @Before
    fun setUp() {
        repository = mock(FullRouteInformationRepository::class.java)
        useCase = FullRouteInformationUseCase(repository)
    }

    @Test
    fun `remote platform domain call`() {
        runBlocking {
            stubRemoteStationEntranceDomain(ModelFactory.generateStationEntranceData())

            val remote = useCase.getStationEntranceData(key, railCode, lineCd, stinCode).singleOrNull()

            assert(remote is DomainStationEntrance)
        }
    }

    @Test
    fun `remote platform domain body null`() {
        runBlocking {
            stubRemoteStationEntranceDomainNull(ModelFactory.generateNullStationEntranceData())

            val remote = useCase.getStationEntranceData(key, railCode, lineCd, stinCode).singleOrNull()

            assertNull(remote?.body)
        }
    }

    suspend fun stubRemoteStationEntranceDomain(data: DomainStationEntrance) {
        whenever(repository.getStationEntranceData(key, railCode, lineCd, stinCode))
            .thenReturn(flowOf(data))
    }

    suspend fun stubRemoteStationEntranceDomainNull(data: DomainStationEntrance) {
        whenever(repository.getStationEntranceData(key, railCode, lineCd, stinCode))
            .thenReturn(flowOf(data))
    }
}