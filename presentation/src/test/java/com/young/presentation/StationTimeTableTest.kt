package com.young.presentation

import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockito_kotlin.whenever
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.usecase.cache.LocalAllStationCodeUseCase
import com.young.domain.usecase.remote.RemoteTimeTableUseCase
import com.young.presentation.consts.CustomTransformationDataMap
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.model.UiTrailTimeTable
import com.young.presentation.viewmodel.StationTimeTableViewModel
import kotlinx.coroutines.flow.combineTransform
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class StationTimeTableTest {

    private lateinit var viewModel : StationTimeTableViewModel
    private lateinit var timeTableUseCase: RemoteTimeTableUseCase
    private lateinit var localAllStationCodeUseCase: LocalAllStationCodeUseCase
    private lateinit var provider: ResourceProvider

    val test = MutableLiveData<UiTrailTimeTable>()

    @Before
    fun setUp() {
        provider = Mockito.mock(ResourceProvider::class.java)
        timeTableUseCase = Mockito.mock(RemoteTimeTableUseCase::class.java)
        localAllStationCodeUseCase = Mockito.mock(LocalAllStationCodeUseCase::class.java)

        viewModel = StationTimeTableViewModel(provider , timeTableUseCase , localAllStationCodeUseCase)
    }

    @Test
    fun call() {
        runBlocking {
            stubDomainTrailTimeTable(generateDomainTrailTimeTable(),"1")
            stubDomainTrailTimeTable(generateDomainTrailTimeTable(),"2")

            val a1 = timeTableUseCase.getSeoulStationTimeTable("","1","","")
            val a2 = timeTableUseCase.getSeoulStationTimeTable("","2","","")

            combineTransform(a1,a2) { a , b ->
                emit(generateUiTrailTimeTable())
            }.collect {
                test.value = it
            }

            val dd = CustomTransformationDataMap(test) {
                it.up
            }

            println(dd.getAwaitValue())
        }
    }

    fun generateUiTrailTimeTable() = UiTrailTimeTable(listOf("1","2","3") , listOf("4","5","6"),"11","22","33","44")
    fun generateDomainTrailTimeTable() = DomainStationTimeTable(listOf("1","2","3") ,"33","44")

    suspend fun stubDomainTrailTimeTable(data : DomainStationTimeTable , code: String) {
        whenever(timeTableUseCase.getSeoulStationTimeTable("",code,"",""))
            .thenReturn(flowOf(data))
    }
}