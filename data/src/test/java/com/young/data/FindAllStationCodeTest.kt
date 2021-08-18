package com.young.data

import androidx.room.Room
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.young.data.dao.AllStationCodeDao
import com.young.data.model.LocalAllStationCodes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FindAllStationCodeTest {

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
            val items = listOf(LocalAllStationCodes("820" , "8" , "2811" , "복정역"))
            dao.insert(items)

            val booleanData = dao.findStationCodes("820")
            val booleanData2 = dao.findStationCodes("819")

            assertThat(booleanData , equalTo(items[0]))
            assertThat(booleanData2 , equalTo(items[0]))
        }
    }

    @Test
    fun `StationCodes Insert Test`() {
        runBlocking(Dispatchers.IO) {
            val items = listOf(
                LocalAllStationCodes("" , "8" , "2811" , "복정역"),
                LocalAllStationCodes( "123", "8" , "2811" , "복정역"),
                LocalAllStationCodes("435" , "8" , "2811" , "복정역"),
                LocalAllStationCodes("6546547" , "8" , "2811" , "복정역")
            )
            val check = dao.insert(items)
        }
    }
}