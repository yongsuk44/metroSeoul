package com.young.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.young.domain.model.DomainRow
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.StationDataUseCase
import com.young.presentation.consts.CustomTransformationDataMap
import com.young.presentation.consts.DayType
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.factory.DataFactory
import com.young.presentation.factory.DataFactory.randomString
import com.young.presentation.factory.ModelFactory.generateDomainRow
import com.young.presentation.factory.ModelFactory.generateDomainStationTimeTable
import com.young.presentation.factory.ModelFactory.generateFlowListDomainStationTimeTable
import com.young.presentation.factory.ModelFactory.generateIndexAllRouteInformation
import com.young.presentation.factory.ModelFactory.generateUiStationTimeTable
import com.young.presentation.mapper.FlowMapper.domainStationTimeTableCombine
import com.young.presentation.model.IndexAllRouteInformation
import com.young.presentation.model.UiStationTimeTable
import com.young.presentation.viewmodel.StationTimeTableViewModel
import junit.framework.Assert.assertNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class StationTimeTableViewModelTest {

    private lateinit var viewModel: StationTimeTableViewModel

    private lateinit var stationDataUseCase: StationDataUseCase
    private lateinit var allStationCodeUseCase: AllStationCodeUseCase
    private lateinit var provider: ResourceProvider

    val test = MutableLiveData<UiStationTimeTable>()

    val code = "code"
    val key = "key"
    val dayCd = "dayCD"
    val railCode = "railCode"
    val lineCode: String = "lineCode"

    @Before
    fun setUp() {
        stationDataUseCase = mock(StationDataUseCase::class.java)
        allStationCodeUseCase = mock(AllStationCodeUseCase::class.java)
        provider = mock(ResourceProvider::class.java)

        viewModel = StationTimeTableViewModel(provider, stationDataUseCase, allStationCodeUseCase)
    }

    @Test
    fun `findStationCode Data Test`() {
        runBlocking {
            stubFindStationCode(generateDomainRow(), randomString())

            val data = viewModel.findAllStationCode(randomString()).singleOrNull()
            val findData = allStationCodeUseCase.findStationCode(randomString()).singleOrNull()

            assertThat(data, `is`(findData))
        }
    }

    @Test
    fun `findStationCode Data is Null Test`() {
        runBlocking {

            stubFindStationCodeNull(generateDomainRow(), code)

            val viewModelData = viewModel.findAllStationCode(code).singleOrNull()
            val useCaseData = allStationCodeUseCase.findStationCode(code).singleOrNull()

            assertNull(useCaseData)
            assertTrue(viewModelData == null && useCaseData == null)
        }
    }

    @Test
    fun `combineSeoulStationTimeTableAPI() AND combinePublicStationTimeTableAPI() is Response Data Success Test`() {
        runBlocking {

            stubTimeTableUseCase()

            val useCaseSeoulData = (1..2).map {
                stationDataUseCase.getSeoulStationTimeTable(
                    key,
                    it.toString(),
                    dayCd,
                    code
                )
            }
                .domainStationTimeTableCombine().singleOrNull()

            val useCasePorTalData = (1..2).map {
                stationDataUseCase.getStationTimetables(
                    key,
                    railCode,
                    dayCd,
                    lineCode,
                    code,
                    it.toString()
                )
            }
                .domainStationTimeTableCombine().singleOrNull()

            val indexData = generateIndexAllRouteInformation(railCode, lineCode, code)

            val viewModelSeoulData = viewModel.combineSeoulStationTimeTableAPI(key, dayCd, code).singleOrNull()
            val viewModelPorTalData = viewModel.combinePublicStationTimeTableAPI(key, dayCd, indexData).singleOrNull()

            assertThat(viewModelSeoulData, `is`(useCaseSeoulData))
            assertThat(viewModelPorTalData, `is`(useCasePorTalData))
        }
    }

    @Test
    fun `findCodeTrueSeoulTimeTableFalsePortalTimeTable() is Null or NotNull Data Test`() {
        runBlocking {

            stubTimeTableUseCase()

            val domainRow = generateDomainRow(code)
            val domainNullRow = null
            val index = generateIndexAllRouteInformation(railCode, lineCode, code)

            val viewModelSeoulData = viewModel.findCodeTrueSeoulTimeTableFalsePortalTimeTable(key , domainRow , dayCd , index).singleOrNull()
            val viewModelPorTalData = viewModel.findCodeTrueSeoulTimeTableFalsePortalTimeTable(key , domainNullRow , dayCd , index).singleOrNull()

            assertTrue(viewModelSeoulData?.up?.first()?.contains("seoul") ?: false)
            assertTrue(viewModelPorTalData?.up?.first()?.contains("public") ?: false)
        }
    }

    @Test
    fun `emptyTimeTableToPortalTimeTableCall() SeoulTimeTable is Null or NotNull Data Test`() {
        runBlocking {

            stubTimeTableUseCase()

            val uiStationTimeTable = generateUiStationTimeTable("seoul")
            val uiStationTimeTableNull = null

            val index = generateIndexAllRouteInformation(railCode, lineCode, code)

            val uiTrailSeoulData = viewModel.emptyTimeTableToPortalTimeTableCall(key , uiStationTimeTable , dayCd , index).singleOrNull()
            val uiTrailPorTalData = viewModel.emptyTimeTableToPortalTimeTableCall(key , uiStationTimeTableNull , dayCd , index).singleOrNull()

            assertTrue(uiTrailSeoulData?.up?.first()?.contains("seoul") ?: false)
            assertTrue(uiTrailPorTalData?.up?.first()?.contains("public") ?: false)
        }
    }

    @Test
    fun `getStationTimeTable() Data Call`() {
        runBlocking {

        }
    }

    suspend fun stubTimeTableUseCase() {
        (1..2).forEach {
            stubSeoulStationTimeTableUseCase(
                generateDomainStationTimeTable("seoul"),
                key,
                it.toString(),
                dayCd,
                code
            )
            stubPublicStationTimeTableUseCase(
                generateDomainStationTimeTable("public"),
                key,
                railCode,
                dayCd,
                lineCode,
                code,
                it.toString()
            )
        }
    }

    suspend fun stubSeoulStationTimeTableUseCase(
        data: DomainStationTimeTable, key: String, upDown: String, dayCd: String, code: String
    ) {
        whenever(stationDataUseCase.getSeoulStationTimeTable(key, upDown, dayCd, code))
            .thenReturn(flowOf(data))
    }

    suspend fun stubPublicStationTimeTableUseCase(
        data: DomainStationTimeTable, key: String, railCode: String, dayCd: String, lineCode: String, stationCode: String, updown: String
    ) {
        whenever(
            stationDataUseCase.getStationTimetables(
                key,
                railCode,
                dayCd,
                lineCode,
                stationCode,
                updown
            )
        )
            .thenReturn(flowOf(data))
    }

    suspend fun stubFindStationCode(data: DomainRow, code: String) {
        whenever(allStationCodeUseCase.findStationCode(code))
            .thenAnswer { flowOf(data) }
    }

    suspend fun stubFindStationCodeNull(data: DomainRow, code: String) {
        whenever(allStationCodeUseCase.findStationCode(code))
            .thenReturn(flowOf(null))
    }
}