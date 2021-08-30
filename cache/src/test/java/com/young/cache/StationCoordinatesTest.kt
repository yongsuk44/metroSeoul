package com.young.cache

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.young.cache.dao.LocationDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class StationCoordinatesTest {

    private lateinit var dao: LocationDao
    private lateinit var db: AppDataBase

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        dao = db.locationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }


}