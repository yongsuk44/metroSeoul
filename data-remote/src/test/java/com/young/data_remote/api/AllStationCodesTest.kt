package com.young.data_remote.api

import com.young.data_remote.TestCoroutineRule
import com.young.data_remote.datasource.RemoteStationTimeDataSourceImpl
import com.young.data_remote.enqueueResponse
import com.young.data_remote.generate.RetrofitFactory
import com.young.data_remote.repository.RemoteStationTelRepositoryImpl
import com.young.data_remote.repository.RemoteStationTimeTableRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
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
class AllStationCodesTest {
    private val mockWebServer = MockWebServer()

    private lateinit var repository: RemoteStationTelRepositoryImpl
    lateinit var publicApi : PublicDataOpenApiService

    @get:Rule
    val rule: TestCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        mockWebServer.start()
        publicApi = RetrofitFactory.RetrofitGenerate(mockWebServer).create(PublicDataOpenApiService::class.java)

        repository = RemoteStationTelRepositoryImpl(publicApi)
    }

    @After
    fun end() {
        mockWebServer.shutdown()
    }

    @Test
    fun `AllStationCodes Success`() {
        mockWebServer.enqueueResponse("AllStationCodesFile", 200)
        runBlocking {
            val call = repository.getStationTelData("test" , "code").single()
            println(call)
        }
    }
}