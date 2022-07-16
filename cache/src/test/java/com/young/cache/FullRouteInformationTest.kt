package com.young.cache

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import com.young.cache.dao.FullRouteInformationDao
import com.young.cache.factory.DataFactory.randomString
import com.young.cache.factory.ModelFactory
import com.young.cache.mapper.CacheToDataMapper.CacheToData
import com.young.cache.repository.CacheFullRouteInformationRepositoryImpl
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsNot
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException
import java.time.LocalTime

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class FullRouteInformationTest {

    private lateinit var dao: FullRouteInformationDao
    private lateinit var db: AppDataBase
    private lateinit var repo: CacheFullRouteInformationRepositoryImpl

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        dao = db.fullRouteInformationDao()

        repo = CacheFullRouteInformationRepositoryImpl(dao)
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    fun `insert FullRouteInformation Data Success`() {
        runBlocking {
            val items = (0..2).map {
                ModelFactory.generateFullRouteInformation(
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString()
                )
            }

            val call = LocalTime.now().toNanoOfDay()
            val insertReturn = repo.insert(items.map { it.CacheToData() }).single()
            val time = LocalTime.now().minusNanos(call)
            println(time)

            assertTrue(insertReturn.all { it > 0 })
            val allData = repo.findStationRouteInformation(null).single()

            assertThat(
                allData.first().railOprIsttCd,
                `is`(items.first().cacheTrailCodeAndLineCode.railOprIsttCd)
            )

            assertThat(
                allData.first().lnCd,
                `is`(items.first().cacheTrailCodeAndLineCode.lnCd)
            )
        }
    }

    @Test
    fun `insert LineCodeAndStationCode Data Success`() {
        runBlocking {
            val items = listOf(
                ModelFactory.generateTrailCodeAndLineCode(randomString(), randomString()),
                ModelFactory.generateTrailCodeAndLineCode(randomString(), randomString()),
                ModelFactory.generateTrailCodeAndLineCode(randomString(), randomString()),
                ModelFactory.generateTrailCodeAndLineCode(randomString(), randomString())
            )

            repo.insertLineCodeAndTrailCode(items.map { it.CacheToData() }).single()

            val allData = repo.readTrailCodeAllData().single()

            val assertAllData = allData.map { it.lnCd to it.railOprIsttCd }
            val assertInsertData = items.map { it.lnCd to it.railOprIsttCd }

            assertThat(assertAllData, `is`(assertInsertData))
        }
    }

    @Test
    fun `select stationName To FullRouteInformation Data`() {
        runBlocking {
            val items = (1..4).map {
                ModelFactory.generateFullRouteInformation(
                    randomString(),
                    "테스트역$it",
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString()
                )
            }

            repo.insert(items.map { it.CacheToData() }).single()

            val selectData = repo.readStationNameToFullRouteInformationData("테스트역1").single()

            assertThat(selectData.stinCd, `is`(items[0].stinCd))
        }
    }

    @Test
    fun `select stationCode To FullRouteInformation Data`() {
        runBlocking {
            val selectCodeList = listOf("코드 1번", "코드 2번")
            val items = (1..3).map {
                ModelFactory.generateFullRouteInformation(
                    "코드 ${it}번",
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString(),
                    randomString()
                )
            }

            repo.insert(items.map { it.CacheToData() }).single()

            val selectData = repo.readStationData(selectCodeList).single()

            assertThat(selectData.last().stinCd, IsNot.not(items.last().stinCd))
            assertThat(selectData.first().stinCd, `is`(items.first().stinCd))
        }
    }
}