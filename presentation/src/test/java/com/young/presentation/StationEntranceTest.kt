package com.young.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.nhaarman.mockito_kotlin.whenever
import com.young.domain.model.DomainStationEntrance
import com.young.domain.usecase.FullRouteInformationUseCase
import com.young.presentation.consts.BaseResult
import com.young.presentation.factory.DataFactory
import com.young.presentation.factory.ModelFactory.generateDomainStationEntrance
import com.young.presentation.factory.ModelFactory.generateDomainStationEntranceNull
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.viewmodel.StationEntranceViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@ExperimentalCoroutinesApi
@FlowPreview
@RunWith(JUnit4::class)
class StationEntranceTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    var key: String = DataFactory.randomString()
    var railCode: String = DataFactory.randomString()
    var lineCd: String = DataFactory.randomString()
    var stinCode: String = DataFactory.randomString()

    private lateinit var viewModel: StationEntranceViewModel
    private lateinit var routeUseCase: FullRouteInformationUseCase

    @Before
    fun setUp() {
        routeUseCase = Mockito.mock(FullRouteInformationUseCase::class.java)
        viewModel = StationEntranceViewModel(routeUseCase)
    }

    @Test
    fun `Domain data == UI data Check`() = runBlockingTest {
        stubStationEntranceData(generateDomainStationEntrance())

        val useCaseData = routeUseCase.getStationEntranceData(key, railCode, lineCd, stinCode).map { it.DomainToUi() }.single()

        viewModel.getStationEntranceData(key, railCode, lineCd, stinCode)
        val liveData = viewModel.stationEntranceData.getAwaitValue() as BaseResult.Success

        assertThat(useCaseData, `is`(liveData.data))
    }

    @Test
    fun `Domain Data Body is Null to Exception Check`() = runBlockingTest {
        stubStationEntranceData(generateDomainStationEntranceNull())

        routeUseCase.getStationEntranceData(key, railCode, lineCd, stinCode).map { it.DomainToUi() }.single()
        viewModel.getStationEntranceData(key, railCode, lineCd, stinCode)
        val liveData = viewModel.stationEntranceData.getAwaitValue() as BaseResult.Failed

        assertThat(liveData.exception , instanceOf(NullPointerException::class.java))
    }


    suspend fun stubStationEntranceData(data: DomainStationEntrance) {
        whenever(routeUseCase.getStationEntranceData(key, railCode, lineCd, stinCode))
            .thenReturn(flowOf(data))
    }
}