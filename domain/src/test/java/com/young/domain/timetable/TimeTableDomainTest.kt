package com.young.domain.timetable

import com.nhaarman.mockito_kotlin.whenever
import com.young.domain.factory.ModelFactory.generatePublicDomainStationTimeTable
import com.young.domain.factory.ModelFactory.generateRowData
import com.young.domain.factory.ModelFactory.generateSeoulDomainStationTimeTable
import com.young.domain.fake.FakeFindStationCodeRepository
import com.young.domain.fake.FakeFullRouteInformationRepository
import com.young.domain.fake.FakeStationTimeTableRepository
import com.young.domain.model.DomainRow
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.repository.AllStationCodesRepository
import com.young.domain.repository.FullRouteInformationRepository
import com.young.domain.repository.StationDataRepository
import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.StationDataUseCase
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class TimeTableDomainTest {

    private lateinit var stationDAtaUseCase: StationDataUseCase
    private lateinit var allStationCodeUseCase: AllStationCodeUseCase

    private lateinit var remoteStationTimeTableRepository: StationDataRepository
    private lateinit var cacheFullRouteInformationRepository: FullRouteInformationRepository

    private lateinit var allStationCodesRepository: AllStationCodesRepository

    @Before
    fun setUp() {
        allStationCodesRepository = mock(FakeFindStationCodeRepository::class.java)
        allStationCodeUseCase = AllStationCodeUseCase(allStationCodesRepository)

        remoteStationTimeTableRepository = mock(FakeStationTimeTableRepository::class.java)
        cacheFullRouteInformationRepository = mock(FakeFullRouteInformationRepository::class.java)
        stationDAtaUseCase = StationDataUseCase(cacheFullRouteInformationRepository, remoteStationTimeTableRepository)
    }

    @Test
    fun `given RowData , when findStationCode, then assert not null`() {
        runBlocking {
            // given
            stubFindStationCode(generateRowData())

            // when
            val item = allStationCodesRepository.findStationCode("code")

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
            val item = allStationCodesRepository.findStationCode("code").single()

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
                remoteStationTimeTableRepository.getSeoulStationTimeTable("key", "11", "22", "33").singleOrNull()

            // then
            Assert.assertNotNull(item)
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
            val item = allStationCodesRepository.findStationCode("code")

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
            val item = allStationCodesRepository.findStationCode("code")

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
        whenever(allStationCodesRepository.findStationCode("code"))
            .thenReturn(flowOf(row))
    }

    suspend fun stubFindStationCodeNull(row: DomainRow) {
        whenever(allStationCodesRepository.findStationCode("code"))
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