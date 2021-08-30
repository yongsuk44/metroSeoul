package com.young.presentation

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockito_kotlin.whenever
import com.young.domain.model.DomainRow
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.StationDataUseCase
import com.young.presentation.consts.CustomTransformationDataMap
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.factory.DataFactory
import com.young.presentation.factory.DataFactory.randomString
import com.young.presentation.factory.ModelFactory.generateDomainRow
import com.young.presentation.factory.ModelFactory.generateDomainTrailTimeTable
import com.young.presentation.model.UiTrailTimeTable
import com.young.presentation.viewmodel.StationTimeTableViewModel
import junit.framework.Assert.assertNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class StationTimeTableViewModelTest {

    private lateinit var viewModel : StationTimeTableViewModel
    private lateinit var stationDataUseCase : StationDataUseCase
    private lateinit var allStationCodeUseCase: AllStationCodeUseCase
    private lateinit var provider: ResourceProvider

    val test = MutableLiveData<UiTrailTimeTable>()

    @Before
    fun setUp() {
        provider = Mockito.mock(ResourceProvider::class.java)
        stationDataUseCase = Mockito.mock(StationDataUseCase::class.java)
        allStationCodeUseCase = Mockito.mock(AllStationCodeUseCase::class.java)

        viewModel = StationTimeTableViewModel(provider , stationDataUseCase , allStationCodeUseCase)
    }

    @Test
    fun `findStationCode Data Test`() {
        runBlocking {
            stubFindStationCode(generateDomainRow(), randomString())

            val data = viewModel.findAllStationCode(randomString()).single()
            val findData = allStationCodeUseCase.findStationCode(randomString()).single()

            assertThat(data , `is`(findData))
        }
    }

    @Test
    fun `findStationCode Data Null Test`() {
        runBlocking {
            stubFindStationCodeNull(generateDomainRow(), randomString())

            val data = viewModel.findAllStationCode(randomString()).single()
            val findData = allStationCodeUseCase.findStationCode(randomString()).single()

            assertNull(findData)
            assertTrue(data == null && findData == null)
        }
    }

    @Test
    fun `findStationCode Null == true SeoulTimeTable`() {
        runBlocking {
            stubFindStationCode(generateDomainRow(), randomString())

            viewModel.findAllStationCode(randomString())
        }
    }

    @Test
    fun `timeTable Data Test`() {
        runBlocking {
            stubDomainTrailTimeTable(generateDomainTrailTimeTable(),"1")
            stubDomainTrailTimeTable(generateDomainTrailTimeTable(),"2")

            val a1 = stationDataUseCase.getSeoulStationTimeTable("","1","","")
            val a2 = stationDataUseCase.getSeoulStationTimeTable("","2","","")


//            viewModel.generateStationTimeTableFlow()

            val dd = CustomTransformationDataMap(test) {
                it.up
            }
        }
    }

    suspend fun stubDomainTrailTimeTable(data : DomainStationTimeTable , code: String) {
        whenever(stationDataUseCase.getSeoulStationTimeTable("",code,"",""))
            .thenReturn(flowOf(data))
    }

    suspend fun stubFindStationCode(data : DomainRow , code : String) {
        whenever(allStationCodeUseCase.findStationCode(code))
            .thenReturn(flowOf(data))
    }

    suspend fun stubFindStationCodeNull(data : DomainRow , code : String) {
        whenever(allStationCodeUseCase.findStationCode(code))
            .thenReturn(flowOf(null))
    }
}