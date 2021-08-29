package com.young.remote.api

import com.young.data.impl.remote.RemoteStationDataSourceImpl
import com.young.remote.enqueueResponse
import com.young.remote.generate.DataFactory.getRandomString
import com.young.remote.generate.RetrofitFactory
import com.young.remote.model.RemoteStationSeoulTimeTable
import com.young.remote.model.RemoteStationTimeTable
import com.young.remote.repository.RemoteStationDataRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.Flow

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StationDataImplTest {

    private val mockWebServer = MockWebServer()

//    private lateinit var datasource : RemoteStationDataSourceImpl
//    private lateinit var repository: RemoteStationDataRepositoryImpl
    lateinit var api: TrailPorTalService
    lateinit var seoulApiService: SeoulApiService
    lateinit var publicApi : PublicDataOpenApiService

    @Before
    fun setUp() {
        mockWebServer.start()

        publicApi = RetrofitFactory.RetrofitGenerate(mockWebServer).create(PublicDataOpenApiService::class.java)
        api = RetrofitFactory.RetrofitGenerate(mockWebServer).create(TrailPorTalService::class.java)
        seoulApiService = RetrofitFactory.RetrofitGenerate(mockWebServer).create(SeoulApiService::class.java)

//        repository = RemoteStationDataRepositoryImpl(publicApi, api , seoulApiService)
//        datasource = RemoteStationDataSourceImpl(repository)
    }

    @After
    fun end() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Success getSeoulStationTimeTable then apiService invoked`() {
        mockWebServer.enqueueResponse("timetable/SeoulTimeTableFile", 200)

        runBlocking {
            val call = generateSeoulStationTimeTable()
            println(call)
        }
    }

    @Test
    fun `Success getRemoteStationTimeTable then apiService invoked`() {
        mockWebServer.enqueueResponse("timetable/TimeTableSuccessFile", 200)

        runBlocking {
            val call = generatePublicStationTimeTable()
            println(call)
        }
    }

    suspend fun generateSeoulStationTimeTable() : RemoteStationSeoulTimeTable =
        seoulApiService.getStationTimeTable(getRandomString(),getRandomString(), getRandomString(), getRandomString())

    suspend fun generatePublicStationTimeTable() : RemoteStationTimeTable =
        api.getStationTimetables(getRandomString(), getRandomString(), getRandomString(),
            getRandomString(), getRandomString(), getRandomString())
}