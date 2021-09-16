package com.young.presentation

import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.FullRouteInformationUseCase
import com.young.presentation.factory.DataFactory
import com.young.presentation.viewmodel.FullRouteInformationViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
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

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    var key: String = DataFactory.randomString()
    var railCode: String = DataFactory.randomString()
    var lineCd: String = DataFactory.randomString()
    var stinCode: String = DataFactory.randomString()

    private lateinit var viewModel : FullRouteInformationViewModel
    private lateinit var routeUseCase : FullRouteInformationUseCase
    private lateinit var codeUseCase : AllStationCodeUseCase


    @Before
    fun setUp() {
        routeUseCase = Mockito.mock(FullRouteInformationUseCase::class.java)
        codeUseCase = Mockito.mock(AllStationCodeUseCase::class.java)

        viewModel = FullRouteInformationViewModel(routeUseCase , codeUseCase)
    }

    @Test
    fun `get fullRouteInformation data check`() {
        mainCoroutineRule.runBlockingTest {
            viewModel.loadFullRouteInformation(key)
        }
    }
}