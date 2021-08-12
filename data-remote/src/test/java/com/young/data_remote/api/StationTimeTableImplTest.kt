package com.young.data_remote.api

import com.young.data_remote.TestCoroutineRule
import com.young.data_remote.datasource.RemoteStationTimeDataSourceImpl
import com.young.data_remote.enqueueResponse
import com.young.data_remote.generate.RetrofitFactory
import com.young.data_remote.mapper.timeTableMapper.RemoteToDomain
import com.young.data_remote.repository.RemoteStationTimeTableRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TrailTimeTableImplTest {

    private val mockWebServer = MockWebServer()

    private lateinit var datasource : RemoteStationTimeDataSourceImpl
    private lateinit var repository: RemoteStationTimeTableRepositoryImpl
    lateinit var api: TrailPorTalService

    @get:Rule
    val rule: TestCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        mockWebServer.start()
        api = RetrofitFactory.RetrofitGenerate(mockWebServer).create(TrailPorTalService::class.java)

        repository = RemoteStationTimeTableRepositoryImpl(
            service = api
        )

        datasource = RemoteStationTimeDataSourceImpl(
            dataSource = repository
        )
    }

    @After
    fun end() {
        mockWebServer.shutdown()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Success getRemoteStationTimeTable then apiService invoked`() {
        mockWebServer.enqueueResponse("TimeTableSuccessFile", 200)

        runBlocking {
            val call = repository.getRemoteStationTimeTable("test","S5", "8", "8", "822").single()
            call
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `RemoteData To DomainData Mapper Test`() {
        mockWebServer.enqueueResponse("TimeTableSuccessFile2", 200)

        runBlocking {
            repository.getRemoteStationTimeTable("test","S5", "8", "8", "822")
                .map {
                    it.RemoteToDomain()
                }.collect {
                    it
                }
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `Success RemoteStationTimeTable then dataSource invoked `() {
        mockWebServer.enqueueResponse("TimeTableSuccessFile", 200)

        runBlocking {
            val call = datasource.getStationTimetables("test","S5", "8", "8", "822").single()
            call
        }
    }
}