package com.young.metro

import androidx.compose.runtime.emit
import com.young.metro.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class TimeStampTest {

    lateinit var localTime: String
    lateinit var localDay: String

    @get:Rule
    val rule = TestCoroutineRule()

    @Before
    fun set() {
        localTime = StringBuilder().append("053321")
            .insert(2, ":")
            .insert(5, ":")
            .toString()

        localDay = StringBuilder().append("20200705")
            .insert(4, "-")
            .insert(7, "-")
            .toString()
    }

    fun getLocalTime(time: String): String = StringBuilder().append(time)
        .insert(2, ":")
        .insert(5, ":")
        .toString()

    @Test
    fun callTEst() {
        val localTimeFormat = DateTimeFormatter.ISO_LOCAL_TIME
        val localDayFormat = DateTimeFormatter.ISO_LOCAL_DATE
        val date = LocalTime.parse(localTime, localTimeFormat)


        val list = listOf(
            LocalTime.parse(getLocalTime("003213"), localTimeFormat),
            LocalTime.parse(getLocalTime("082213"), localTimeFormat),
            LocalTime.parse(getLocalTime("230045"), localTimeFormat),
            LocalTime.parse(getLocalTime("004543"), localTimeFormat),
            LocalTime.parse(getLocalTime("012345"), localTimeFormat),
            LocalTime.parse(getLocalTime("110321"), localTimeFormat)
        )

        list.groupBy {
            it.hour == 0
        }.run {
            val reduceList = MutableList<List<LocalTime>>(size) {
                if (it == 0) getValue(false).sorted()
                else getValue(true).sorted()
            }.reduce { a, b -> a+b }

//            println(reduceList)
        }

        list.groupBy {
            it.hour == 0
        }.run {
            MutableList<List<LocalTime>>(size) { index ->
                if (index == 0) getValue(false).sorted()
                else getValue(true).sorted()
            }.reduce { a, b -> a+b }
        }.also {

            val max = it.last()
            val min = it.first()
            println(max)
            println(min)
        }



        val date2 = LocalDate.parse(localDay, localDayFormat)

//        println(date)
//        println("hour ${date.hour}")
//        println("minute ${date.minute}")
//        println("second ${date.second}")
//
//        println(date2)
//        println("year ${date2.dayOfYear}")
//        println("month ${date2.dayOfMonth}")
//        println("day ${date2.dayOfWeek}")

    }

    fun <T, K> Flow<T>.groupBy(getKey : (T) -> K): Flow<Pair<K, List<T>>> = flow {
        val storage = mutableMapOf<K, MutableList<T>>()

        collect { t ->
            storage.getOrPut(getKey(t)) {
                mutableListOf()
            } += t
        }

        storage.forEach { (k, ts) ->
            emit(k to ts)
        }
    }
}