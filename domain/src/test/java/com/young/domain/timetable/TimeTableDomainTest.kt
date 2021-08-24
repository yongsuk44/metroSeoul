package com.young.domain.timetable

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockito_kotlin.whenever
import com.young.domain.factory.timetableFactory.generatePublicDomainStationTimeTable
import com.young.domain.factory.timetableFactory.generateRowData
import com.young.domain.factory.timetableFactory.generateSeoulDomainStationTimeTable
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.model.Row
import com.young.domain.repository.location.CacheAllStationCodesRepository
import com.young.domain.repository.remote.RemoteStationTimeTableRepository
import com.young.domain.usecase.cache.CacheAllStationCodeUseCase
import com.young.domain.usecase.remote.RemoteTimeTableUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class TimeTableDomainTest {

    private lateinit var remoteStationTimeTableUseCaseImpl: RemoteTimeTableUseCase
    private lateinit var CacheAllStationCodeUseCase: CacheAllStationCodeUseCase

    private lateinit var remoteStationTimeTableRepository: RemoteStationTimeTableRepository
    private lateinit var CacheAllStationCodesRepository: CacheAllStationCodesRepository

    @Before
    fun setUp() {
        CacheAllStationCodesRepository = mock(FakeFindStationCodeRepository::class.java)
        remoteStationTimeTableRepository = mock(FakeStationTimeTableRepository::class.java)

        CacheAllStationCodeUseCase = CacheAllStationCodeUseCase(CacheAllStationCodesRepository)
        remoteStationTimeTableUseCaseImpl = RemoteTimeTableUseCase(remoteStationTimeTableRepository)
    }

    @Test
    fun `given RowData , when findStationCode, then assert not null`() {
        runBlocking {
            // given
            stubFindStationCode(generateRowData())

            // when
            val item = CacheAllStationCodeUseCase.findStationCode("code")

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
            val item = CacheAllStationCodeUseCase.findStationCode("code").single()

            // then
            assertThat(item , CoreMatchers.equalTo(generateItem))
        }
    }

    @Test
    fun `given timetableData , when getSeoulStationTimeTable, then assert null`() {
        runBlocking {
            // given
            stubStationSeoulTimeTable(generateSeoulDomainStationTimeTable())

            // when
            val item = remoteStationTimeTableUseCaseImpl.getSeoulStationTimeTable("key" , "11" , "22" , "33")

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
            val item = CacheAllStationCodeUseCase.findStationCode("code")

            // then
            item.flatMapConcat {
                if (it == null) remoteStationTimeTableUseCaseImpl.getStationTimetables("key" , "rail" , "day" , "line" ,"scd" , "updowncode")
                else remoteStationTimeTableUseCaseImpl.getSeoulStationTimeTable("key" , "11" , "22" , "33")
            }.collect {
                it
            }
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
            val item = CacheAllStationCodeUseCase.findStationCode("code")

            // then
            item.flatMapConcat {
                if (it == null) remoteStationTimeTableUseCaseImpl.getStationTimetables("key" , "rail" , "day" , "line" ,"scd" , "updowncode")
                else remoteStationTimeTableUseCaseImpl.getSeoulStationTimeTable("key" , "11" , "22" , "33")
            }.collect {
                it
            }
        }
    }

    suspend fun stubFindStationCode(row : Row) {
        whenever(CacheAllStationCodesRepository.findStationCode("code"))
            .thenReturn(flowOf(row))
    }

    suspend fun stubFindStationCodeNull(row : Row) {
        whenever(CacheAllStationCodesRepository.findStationCode("code"))
            .thenReturn(flowOf(null))
    }

    suspend fun stubStationPublicTimeTable(data : DomainStationTimeTable) {
        whenever(remoteStationTimeTableRepository.getStationTimetables("key" , "rail" , "day" , "line" ,"scd" , "updowncode"))
            .thenReturn(flowOf(data))
    }

    suspend fun stubStationPublicTimeTableNull(data : DomainStationTimeTable) {
        whenever(remoteStationTimeTableRepository.getStationTimetables("key" , "rail" , "day" , "line" ,"scd" , "updowncode"))
            .thenReturn(null)
    }

    suspend fun stubStationSeoulTimeTable(data : DomainStationTimeTable) {
        whenever(remoteStationTimeTableRepository.getSeoulStationTimeTable("key" , "11" , "22" , "33"))
            .thenReturn(flowOf(data))
    }

    suspend fun stubStationSeoulTimeTableNull(data : DomainStationTimeTable) {
        whenever(remoteStationTimeTableRepository.getSeoulStationTimeTable("key" , "11" , "22" , "33"))
            .thenReturn(null)
    }
}