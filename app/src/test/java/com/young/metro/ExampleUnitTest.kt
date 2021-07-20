package com.young.metro

import org.junit.Test
import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    val list = listOf("사당","서울시청","가락시장","서울역","서울대입구")

    @Test
    fun filterTest() {
        println("사당".toCharArray())
        println()
        list.filter {
            it.toLowerCase().contains("서울ㅇ".toLowerCase())
        }.run {
            println(this)
        }
    }
}