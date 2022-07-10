package com.young.cache

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.young.cache.dao.LocationDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class StationCoordinatesTest {

    private lateinit var dao: LocationDao
    private lateinit var db: AppDataBase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase::class.java).build()
        dao = db.locationDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }


}