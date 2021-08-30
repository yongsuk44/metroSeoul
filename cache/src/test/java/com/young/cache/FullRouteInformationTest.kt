package com.young.cache

import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.young.cache.dao.FullRouteInformationDao
import com.young.cache.factory.DataFactory.randomString
import com.young.cache.factory.ModelFactory
import com.young.cache.factory.runBlockIOTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNot
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FullRouteInformationTest {

    private lateinit var dao: FullRouteInformationDao
    private lateinit var db: AppDataBase

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        dao = db.fullRouteInformationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    fun `insert FullRouteInformation Data Success`() {
        runBlockIOTest {
            val items = listOf(
                ModelFactory.generateFullRouteInformation(randomString() , randomString() ,randomString() ,randomString() , randomString() ,randomString()),
                ModelFactory.generateFullRouteInformation(randomString() , randomString() ,randomString() ,randomString() , randomString() ,randomString()),
                ModelFactory.generateFullRouteInformation(randomString() , randomString() ,randomString() ,randomString() , randomString() ,randomString())
            )
            dao.insertFullRouteInformation(items)
            val allData = dao.getAllData()

            assertThat(
                allData.first().cacheTrailCodeAndLineCode.railOprIsttCd ,
                `is`(items.first().cacheTrailCodeAndLineCode.railOprIsttCd)
            )

            assertThat(
                allData.first().cacheTrailCodeAndLineCode.lnCd ,
                `is`(items.first().cacheTrailCodeAndLineCode.lnCd)
            )
        }
    }

    @Test
    fun `insert LineCodeAndStationCode Data Success`() {
        runBlocking(Dispatchers.IO) {
            val items = listOf(
                ModelFactory.generateTrailCodeAndLineCode(randomString() , randomString()) ,
                ModelFactory.generateTrailCodeAndLineCode(randomString() , randomString()),
                ModelFactory.generateTrailCodeAndLineCode(randomString() , randomString()),
                ModelFactory.generateTrailCodeAndLineCode(randomString() , randomString())
            )

            dao.insertLineCodeAndTrailCode(items)
            val allData = dao.getTrailCodeAllData()

            val assertAllData = allData.map { it.lnCd to it.railOprIsttCd }
            val assertInsertData = items.map { it.lnCd to it.railOprIsttCd }

            assertThat(assertAllData , `is`(assertInsertData))
        }
    }

    @Test
    fun `select stationName To FullRouteInformation Data`() {
        runBlockIOTest {
            val items = listOf(
                ModelFactory.generateFullRouteInformation(randomString() , "테스트역1" ,randomString() ,randomString() , randomString() ,randomString()),
                ModelFactory.generateFullRouteInformation(randomString() , "테스트역2" ,randomString() ,randomString() , randomString() ,randomString()),
                ModelFactory.generateFullRouteInformation(randomString() , "테스트역1" ,randomString() ,randomString() , randomString() ,randomString())
            )
            dao.insertFullRouteInformation(items)
            val selectData = dao.getStationNameToFullRouteInformationData("테스트역1")

            assertThat(selectData.stinCd , `is`(items[0].stinCd))
        }
    }

    @Test
    fun `select stationCode To FullRouteInformation Data`() {
        runBlockIOTest {
            val selectCodeList = listOf("코드 1번" , "코드 2번")
            val items = listOf(
                ModelFactory.generateFullRouteInformation("코드 1번" ,  randomString(),randomString() ,randomString() , randomString() ,randomString()),
                ModelFactory.generateFullRouteInformation("코드 2번" , randomString() ,randomString() ,randomString() , randomString() ,randomString()),
                ModelFactory.generateFullRouteInformation("코드 3번" , randomString() ,randomString() ,randomString() , randomString() ,randomString())
            )

            dao.insertFullRouteInformation(items)
            val selectData = dao.getStationData(selectCodeList)

            assertThat(selectData.last().stinCd , IsNot.not(items.last().stinCd))
            assertThat(selectData.first().stinCd , `is`(items.first().stinCd))
        }
    }
}