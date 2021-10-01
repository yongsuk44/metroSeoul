package com.young.remote.api

import com.young.remote.TestCoroutineRule
import com.young.remote.enqueueResponse
import com.young.remote.generate.DataFactory.getRandomString
import com.young.remote.generate.RetrofitFactory
import com.young.remote.mapper.RemoteToDataMapper.RemoteToData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FullRouteInformationImplTest {
    private val mockWebServer = MockWebServer()

    private lateinit var seoulApiService: SeoulApiService
    private lateinit var service: TrailPorTalService

    @get:Rule
    val rule: TestCoroutineRule = TestCoroutineRule()

    var key: String = getRandomString()
    var railCode: String = getRandomString()
    var lineCd: String = getRandomString()
    var stinCode: String = getRandomString()
    @Before
    fun setUp() {
        mockWebServer.start()

        seoulApiService = RetrofitFactory.RetrofitGenerate(mockWebServer).create(SeoulApiService::class.java)
        service = RetrofitFactory.RetrofitGenerate(mockWebServer).create(TrailPorTalService::class.java)
    }

    @After
    fun end() {
        mockWebServer.shutdown()
    }

    @Test
    fun `AllStationCodes Success`() {
        mockWebServer.enqueueResponse("AllStationCodesFile", 200)

        runBlocking {
            val call = seoulApiService.getStationNameToAllStationCodes(getRandomString())
            call.SearchInfoBySubwayNameService.row.forEach {
                println(it)
            }
        }
    }

    @Test
    fun `getConvenienceInformation Success`() {
        mockWebServer.enqueueResponse("ConvenienceInformation", 200)

        runBlocking {
            val call = service.getConvenienceInformation(getRandomString() , getRandomString() , getRandomString() , getRandomString(), getRandomString())
            call.body.forEach {
                println(it)
            }
        }
    }

    @Test
    fun `getPlatformEntranceData Success`() {
        mockWebServer.enqueueResponse("EntranceData2", 200)

        runBlocking {
            val api = service.getPlatformEntranceData(key, "json",railCode, lineCd, stinCode)

            println(api)
            println(api.RemoteToData())
        }
    }

    @Test
    fun `getStationRouteInformation Success`() {
        mockWebServer.enqueueResponse("StationRouteInformation", 200)

        runBlocking {
            val call = service.getStationRouteInformation(getRandomString() , getRandomString() , getRandomString() , null)
            call.body.forEach {
                println(it)
            }
        }
    }
}