package com.young.domain.timetable

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockito_kotlin.whenever
import com.young.domain.factory.timetableFactory.generatePublicDomainStationTimeTable
import com.young.domain.factory.timetableFactory.generateRowData
import com.young.domain.factory.timetableFactory.generateSeoulDomainStationTimeTable
import com.young.domain.model.DomainRow
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.repository.location.CacheAllStationCodesRepository
import com.young.domain.repository.location.CacheFullRouteInformationRepository
import com.young.domain.repository.remote.RemoteStationDataRepository
import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.StationDataUseCase
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class TimeTableDomainTest {

    private lateinit var stationDAtaUseCase : StationDataUseCase
    private lateinit var allStationCodeUseCase : AllStationCodeUseCase

    private lateinit var remoteStationTimeTableRepository: RemoteStationDataRepository
    private lateinit var cacheFullRouteInformationRepository : CacheFullRouteInformationRepository

    private lateinit var cacheAllStationCodesRepository : CacheAllStationCodesRepository
    @Before
    fun setUp() {
        cacheAllStationCodesRepository = mock(FakeFindStationCodeRepository::class.java)
        allStationCodeUseCase = AllStationCodeUseCase(cacheAllStationCodesRepository)

        remoteStationTimeTableRepository = mock(FakeStationTimeTableRepository::class.java)
        cacheFullRouteInformationRepository = mock(FakeCacheFullRouteInformationRepository::class.java)
        stationDAtaUseCase = StationDataUseCase(cacheFullRouteInformationRepository , remoteStationTimeTableRepository)
    }

    @Test
    fun `given RowData , when findStationCode, then assert not null`() {
        runBlocking {
            // given
            stubFindStationCode(generateRowData())

            // when
            val item = cacheAllStationCodesRepository.findStationCode("code")

            // then
            Assert.assertNotNull(item)
        }
    }

    @Test
    fun `given RowData , when findStationCode, then assert equals`() {
        runBlocking {
            // given
            val generateItem = generateRowData()
            stubFindStationCode(generateItem)

            // when
            val item = cacheAllStationCodesRepository.findStationCode("code").single()

            // then
            assertThat(item, CoreMatchers.equalTo(generateItem))
        }
    }

    @Test
    fun `given timetableData , when getSeoulStationTimeTable, then assert null`() {
        runBlocking {
            // given
            stubStationSeoulTimeTable(generateSeoulDomainStationTimeTable())

            // when
            val item =
                remoteStationTimeTableRepository.getSeoulStationTimeTable("key", "11", "22", "33")

            // then
            Assert.assertNull(item)
        }
    }

    @Test
    fun `given findStationCode Null seoulData Null publicData Use, when findStationCode, then publicData Use`() {
        runBlocking {
            // given
            stubFindStationCodeNull(generateRowData())
            stubStationSeoulTimeTableNull(generateSeoulDomainStationTimeTable())
            stubStationPublicTimeTable(generatePublicDomainStationTimeTable())

            // when
            val item = cacheAllStationCodesRepository.findStationCode("code")

            // then
            item.flatMapConcat {
                if (it == null) remoteStationTimeTableRepository.getStationTimetables(
                    "key",
                    "rail",
                    "day",
                    "line",
                    "scd",
                    "updowncode"
                )
                else remoteStationTimeTableRepository.getSeoulStationTimeTable(
                    "key",
                    "11",
                    "22",
                    "33"
                )
            }.single()
        }
    }

    @Test
    fun `given findStationCode Use seoulData use publicData Use, when findStationCode, then seoulData Use`() {
        runBlocking {
            // given
            stubFindStationCode(generateRowData())
            stubStationSeoulTimeTable(generateSeoulDomainStationTimeTable())
            stubStationPublicTimeTable(generatePublicDomainStationTimeTable())

            // when
            val item = cacheAllStationCodesRepository.findStationCode("code")

            // then
            item.flatMapConcat {
                if (it == null) remoteStationTimeTableRepository.getStationTimetables(
                    "key",
                    "rail",
                    "day",
                    "line",
                    "scd",
                    "updowncode"
                )
                else remoteStationTimeTableRepository.getSeoulStationTimeTable(
                    "key",
                    "11",
                    "22",
                    "33"
                )
            }.single()
        }
    }

    suspend fun stubFindStationCode(row: DomainRow) {
        whenever(cacheAllStationCodesRepository.findStationCode("code"))
            .thenReturn(flowOf(row))
    }

    suspend fun stubFindStationCodeNull(row: DomainRow) {
        whenever(cacheAllStationCodesRepository.findStationCode("code"))
            .thenReturn(flowOf(null))
    }

    suspend fun stubStationPublicTimeTable(data: DomainStationTimeTable) {
        whenever(
            remoteStationTimeTableRepository.getStationTimetables(
                "key",
                "rail",
                "day",
                "line",
                "scd",
                "updowncode"
            )
        )
            .thenReturn(flowOf(data))
    }

    suspend fun stubStationPublicTimeTableNull(data: DomainStationTimeTable) {
        whenever(
            remoteStationTimeTableRepository.getStationTimetables(
                "key",
                "rail",
                "day",
                "line",
                "scd",
                "updowncode"
            )
        )
            .thenReturn(null)
    }

    suspend fun stubStationSeoulTimeTable(data: DomainStationTimeTable) {
        whenever(remoteStationTimeTableRepository.getSeoulStationTimeTable("key", "11", "22", "33"))
            .thenReturn(flowOf(data))
    }

    suspend fun stubStationSeoulTimeTableNull(data: DomainStationTimeTable) {
        whenever(remoteStationTimeTableRepository.getSeoulStationTimeTable("key", "11", "22", "33"))
            .thenReturn(null)
    }
}