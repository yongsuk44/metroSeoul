package com.young.data_remote.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.young.data_remote.repository.RemoteTrailTimeTableRepositoryImpl
import com.young.domain.repository.timetable.RemoteTrailTimeTableRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import kotlin.math.ln

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TrailTimeTableImplTest {

    private lateinit var repository : RemoteTrailTimeTableRepository

    @get:Rule
    val rule : TestRule = InstantTaskExecutorRule()


    @Mock
    lateinit var service : TrailPorTalService

    @Before
    fun setUp() {
        repository = RemoteTrailTimeTableRepositoryImpl(
            service = service
        )
    }

    @Test
    fun `When getTrailTimeTable then apiService invoked`() {
        runBlockingTest {
//            whenever(service.getTrailTimetables(testKey,"json",railOprIsttCd,dayCd, lnCd,stinCd)).thenReturn(mock())
//
//            repository.getTrailTimetables(testKey,railOprIsttCd,dayCd, lnCd,stinCd)
//
//            verify(service , times(1)).getTrailTimetables(testKey,"json",railOprIsttCd,dayCd, lnCd,stinCd)
        }
    }
}