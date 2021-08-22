package com.young.data_remote.api

import com.young.data_remote.TestCoroutineRule
import com.young.data_remote.datasource.RemoteStationTimeDataSourceImpl
import com.young.data_remote.enqueueResponse
import com.young.data_remote.generate.RetrofitFactory
import com.young.data_remote.mapper.RemoteToDomainMapper.RemoteToDomain
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
import java.time.LocalTime
import kotlin.math.abs

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class TrailTimeTableImplTest {

    private val mockWebServer = MockWebServer()

    private lateinit var datasource : RemoteStationTimeDataSourceImpl
    private lateinit var repository: RemoteStationTimeTableRepositoryImpl
    lateinit var api: TrailPorTalService
    lateinit var seoulApiService: SeoulApiService

    @get:Rule
    val rule: TestCoroutineRule = TestCoroutineRule()

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
                }.map {
                    it.body.nowTimeNearList()
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


    fun List<String>.nowTimeNearList() : Int {
        val min = 1000
        val target = LocalTime.now().toSecondOfDay()

        find { s ->
            val dd = LocalTime.parse(s).toSecondOfDay() - target
            println("$s : ${abs(dd)}")
            abs(min) > abs(dd)
        }.run {
            return this@nowTimeNearList.indexOf(this)
        }
    }
}