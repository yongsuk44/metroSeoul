package com.young.metro

import org.junit.Test

class ListTest {
    val list = listOf(
        ddddddd("1","line1",1,"dd"),
        ddddddd("3" , "line7",2,"asdas"),
        ddddddd("1" , "line1",2,"asdas"),
        ddddddd("3" , "line3",2,"asdas")
    )

    @Test
    fun test() {
        println(
            list.asSequence()
                .map {
                    dd(it.line , it.code)
                }.toList().distinct()
        )
    }

    data class dd(val line : String , val code: String)
    data class ddddddd(val line : String , val code: String , val age :Int ,val test: String)
}