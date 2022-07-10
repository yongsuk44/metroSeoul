package com.young.cache

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.young.cache.dao.AllStationCodeDao
import com.young.cache.factory.DataFactory.randomString
import com.young.cache.factory.ModelFactory.generateAllStationCodes
import com.young.cache.mapper.CacheToDataMapper.CacheToData
import com.young.cache.repository.CacheAllStationCodesRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class AllStationCodeTest {

    private lateinit var dao : AllStationCodeDao
    private lateinit var db : AppDataBase
    private lateinit var repo : CacheAllStationCodesRepositoryImpl

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context , AppDataBase::class.java).build()
        dao = db.allStationCodeDao()

        repo = CacheAllStationCodesRepositoryImpl(dao, Dispatchers.IO)
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    fun `Find StationCode Return To Boolean Data Check`() {
        runBlocking {
            val items = listOf(generateAllStationCodes("820" , randomString() , randomString(), randomString()))

            repo.insert(items.map { it.CacheToData()!! }).single()

            val booleanData = repo.findStationCode("820").single()
            val booleanData2 = repo.findStationCode("819").single()

            assertThat(booleanData?.FR_CODE , equalTo(items.first().FR_CODE))
            assertThat(booleanData2?.FR_CODE , nullValue())
        }
    }

    @Test
    fun `StationCodes Insert Test`() {
        runBlocking {
            val items = (1..4).map {
                generateAllStationCodes(it.toString() , randomString() , randomString() , randomString())
            }

            repo.insert(items.map { it.CacheToData()!! }).single()
            val data = repo.findStationCode("1").single()

            assertThat(data?.FR_CODE , equalTo(items.first().FR_CODE))
        }
    }
}