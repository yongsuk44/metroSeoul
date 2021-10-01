package com.young.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockito_kotlin.whenever
import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.model.DomainPlatformEntrance
import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.FullRouteInformationUseCase
import com.young.presentation.factory.DataFactory
import com.young.presentation.factory.ModelFactory.generateDomainPlatformEntrance
import com.young.presentation.viewmodel.DetailDropBoxItemViewModel
import com.young.presentation.viewmodel.FullRouteInformationViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.single
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
class PlatformEntranceTest {

    var key: String = DataFactory.randomString()
    var railCode: String = DataFactory.randomString()
    var lineCd: String = DataFactory.randomString()
    var stinCode: String = DataFactory.randomString()

    private lateinit var viewModel : DetailDropBoxItemViewModel
    private lateinit var routeUseCase : FullRouteInformationUseCase

    @Before
    fun setUp() {
        routeUseCase = Mockito.mock(FullRouteInformationUseCase::class.java)

        viewModel = DetailDropBoxItemViewModel(routeUseCase)
    }

    @Test
    fun `get Cache Full RouteInformation data Success`() {
        runBlocking {
            stubPlatformEntranceData(generateDomainPlatformEntrance())

            viewModel.getPlatformEntranceData(key, railCode, lineCd, stinCode)
        }
    }

    suspend fun stubPlatformEntranceData(data : DomainPlatformEntrance) {
        whenever(routeUseCase.getPlatformEntranceData(key, railCode, lineCd, stinCode))
            .thenReturn(flowOf(data))
    }
}