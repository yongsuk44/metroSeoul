package com.young.cache

import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.young.cache.dao.AllStationCodeDao
import com.young.cache.factory.DataFactory.randomString
import com.young.cache.factory.ModelFactory.generateAllStationCodes
import com.young.cache.model.CacheAllStationCodes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AllStationCodeTest {

    private lateinit var dao : AllStationCodeDao
    private lateinit var db : AppDataBase

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context , AppDataBase::class.java).build()
        dao = db.allStationCodeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    fun `Find StationCode Return To Boolean Data Check`() {
        runBlocking(Dispatchers.IO) {
            val items = listOf(generateAllStationCodes("820" , randomString() , randomString(), randomString()))
            dao.insert(items)

            val booleanData = dao.findStationCodes("820")
            val booleanData2 = dao.findStationCodes("819")

            assertThat(booleanData , equalTo(items[0]))
            assertThat(booleanData2 , nullValue())
        }
    }

    @Test
    fun `StationCodes Insert Test`() {
        runBlocking(Dispatchers.IO) {
            val items = listOf(
                CacheAllStationCodes("" , "8" , "2811" , "복정역"),
                CacheAllStationCodes( "123", "8" , "2811" , "복정역"),
                CacheAllStationCodes("435" , "8" , "2811" , "복정역"),
                CacheAllStationCodes("6546547" , "8" , "2811" , "복정역")
            )

            dao.insert(items)

            assertThat(dao.getAllStationCodeData() , equalTo(items))
        }
    }
}