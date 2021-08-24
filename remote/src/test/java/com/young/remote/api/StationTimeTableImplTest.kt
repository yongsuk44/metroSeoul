package com.young.remote.api

import com.young.remote.datasource.RemoteStationTimeDataSourceImpl
import com.young.remote.enqueueResponse
import com.young.remote.generate.RetrofitFactory
import com.young.cache.mapper.RemoteToDomainMapper.RemoteToDomain
import com.young.remote.repository.RemoteStationTimeTableRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
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
    lateinit var seoulApiService: SeoulApiService

    @Before
    fun setUp() {
        mockWebServer.start()

        api = RetrofitFactory.RetrofitGenerate(mockWebServer).create(TrailPorTalService::class.java)
        seoulApiService = RetrofitFactory.RetrofitGenerate(mockWebServer).create(SeoulApiService::class.java)

        repository = RemoteStationTimeTableRepositoryImpl(
            service = api , seoulApiService
        )

        datasource = RemoteStationTimeDataSourceImpl(
            dataSource = repository
        )
    }

    @After
    fun end() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Success getSeoulStationTimeTable then apiService invoked`() {
        mockWebServer.enqueueResponse("timetable/SeoulTimeTableFile", 200)

        runBlocking {
            repository.getRemoteSeoulStationTimeTable("test","S5", "8", "8").map {
                it.RemoteToDomain()
            }.collect {
                it
            }
        }
    }

    @Test
    fun `Success getRemoteStationTimeTable then apiService invoked`() {
        mockWebServer.enqueueResponse("timetable/TimeTableSuccessFile", 200)

        runBlocking {
            val call = repository.getRemoteStationTimeTable("test","S5", "8", "8", "822" , "1").single()
            call.body?.forEach {
                println(it)
            }
        }
    }

    @Test
    fun `RemoteData To DomainData Mapper Test`() {
        mockWebServer.enqueueResponse("timetable/TimeTableSuccessFile2", 200)

        runBlocking {
            repository.getRemoteStationTimeTable("test","S5", "8", "8", "822" , "1")
                .map {
                    it.RemoteToDomain("1")
                }.collect {
                    println(it)
                }
        }
    }

    @Test
    fun `Success RemoteStationTimeTable then dataSource invoked `() {
        mockWebServer.enqueueResponse("timetable/TimeTableSuccessFile", 200)

        runBlocking {
            val call = datasource.getStationTimetables("test","S5", "8", "8", "822", "1").single()
            call
        }
    }
}