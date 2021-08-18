package com.young.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.young.domain.usecase.local.LocalAllStationCodeUseCase
import com.young.domain.usecase.remote.RemoteTimeTableUseCase
import com.young.domain.usecase.remote.RemoteTimeTableBaseUseCase
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.model.IndexAllRouteInformation
import com.young.presentation.viewmodel.StationTimeTableViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class StationTimeTableTest {

    private lateinit var viewModel : StationTimeTableViewModel
    private lateinit var timeTableUseCase: RemoteTimeTableBaseUseCase
    private lateinit var localAllStationCodeUseCase: LocalAllStationCodeUseCase
    private lateinit var provider: ResourceProvider

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        provider = Mockito.mock(ResourceProvider::class.java)
        timeTableUseCase = Mockito.mock(RemoteTimeTableUseCase::class.java)
        localAllStationCodeUseCase = Mockito.mock(LocalAllStationCodeUseCase::class.java)

        viewModel = StationTimeTableViewModel(provider, timeTableUseCase, localAllStationCodeUseCase)
    }

    @Test
    fun call() {
        runBlocking {

        }
    }
}