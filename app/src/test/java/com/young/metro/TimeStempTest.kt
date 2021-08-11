package com.young.metro

import androidx.compose.runtime.emit
import com.young.presentation.model.UiTrailTimeTable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
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
        .insert(time.length-4, ":")
        .insert(time.length-1, ":")
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


    }

    @Test
    fun coroutineTest() {
        runBlockingTest {
            flowOf(listOf("012345" ,"110321", "082213" , "003213" , "230045" , "060900"))
//                .flatMapConcat {
//                    flowOf(
//                        it.map {
//                            LocalTime.parse(getLocalTime(it), DateTimeFormatter.ISO_LOCAL_TIME)
//                        }
//                    )
//                }
                .transform {
                    it.map { getLocalTime(it) }.run {
                        emit(filter { it.substring(0..1).toInt() > 1 }.sorted())
                        emit(filter { it.substring(0..1).toInt() <= 1 }.sorted())
                    }
                }
                .map {
                    val min = it.first().toString()
                    val max = it.last().toString()
                    println(min)
                    println(max)
                    println(it)
                }.single()
        }
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