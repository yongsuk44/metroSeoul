package com.young.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockito_kotlin.whenever
import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.model.DomainStationEntrance
import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.FullRouteInformationUseCase
import com.young.presentation.factory.DataFactory
import com.young.presentation.factory.ModelFactory.generateDomainStationEntrance
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.model.StationEntranceBody
import com.young.presentation.model.UiStationEntrance
import com.young.presentation.viewmodel.StationEntranceViewModel
import com.young.presentation.viewmodel.FullRouteInformationViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
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
    fun `Domain data Mapper to UI data`() {
        runBlocking {
            stubStationEntranceData(generateDomainStationEntrance())

            routeUseCase.getStationEntranceData(key, railCode, lineCd, stinCode)
                .map { it.DomainToUi() }
                .collect { println(it) }
        }
    }

    suspend fun stubStationEntranceData(data: DomainStationEntrance) {
        whenever(routeUseCase.getStationEntranceData(key, railCode, lineCd, stinCode))
            .thenReturn(flowOf(data))
    }
}