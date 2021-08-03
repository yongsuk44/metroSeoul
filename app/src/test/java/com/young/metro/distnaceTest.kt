package com.young.metro

import org.junit.Test
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

class distanceTest {
    val x = 37.9278947
    val y = 127.06112479

    val x2 = 37.4873499
    val y2 = 127.1018416

    @Test
    fun test() {
        listOf(
            dummyData(37.9478947, 127.06112479999999),
            dummyData(37.9277486, 127.05470979999998),
            dummyData(37.5246, 126.87433619999999)
        ).forEach {
            call(it.mapX, it.mapY)
        }
    }

    fun call(mapX: Double, mapY: Double) {
        val localSinX = sin(Math.toRadians(mapX))
        val localSinY = sin(Math.toRadians(mapY))
        val localCosX = cos(Math.toRadians(mapX))
        val localCosY = cos(Math.toRadians(mapY))
        // lat1 = x , lng1 = y 현재 위치
        val partialDistance = (localCosX * cos(Math.toRadians(x))) *
                (localCosY * cos(Math.toRadians(y))) + (sin(Math.toRadians(y)) * localSinY) +
                (localSinX * sin(Math.toRadians(x)))

        val partialDistance2 = (localCosX * cos(Math.toRadians(x2))) *
                (localCosY * cos(Math.toRadians(y2))) + (sin(Math.toRadians(y2)) * localSinY) +
                (localSinX * sin(Math.toRadians(x2)))

//        print( 6371 * acos(partialDistance))
//        println()
//        print( 6371 * acos(partialDistance2))
        println("$partialDistance2      $partialDistance")
        println(partialDistance2 >= cos(2.0/6371))


    }

    data class dummyData(
        val mapX: Double,
        val mapY: Double
    )
}