package com.young.metro

import com.young.presentation.consts.STATION_LINE
import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    val list = listOf("사당","서울시청","가락시장","서울역","서울대입구")
    val list2 = listOf("복정" , "복정(데이터)" , "복정(더미)")

    @Test
    fun filterTest() {
        list2.map {
            if (it.indexOf('(') != -1) it.removeRange(it.indexOf('(') , it.length)
            else it
        }.distinctBy {
            it
        }.run {
            println(this)
        }
    }

    @Test
    fun colorTest() {
        println(
            STATION_LINE.LINE_1.getColor()
        )
    }
}