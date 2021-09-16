package com.young.remote.generate

import com.young.data.model.DataHeader
import com.young.data.model.DataPlatformEntrance
import com.young.data.model.DataPlatformEntranceBody
import java.util.*
import kotlin.random.Random

object DataFactory {

    fun getRandomString(): String {
        return UUID.randomUUID().toString().substring(0, 15)
    }

    fun getRandomInt(): Int {
        return Random.nextInt()
    }

    fun getRandomLong(): Long {
        return Random.nextLong()
    }

    fun getRandomDouble(): Double {
        return Random.nextDouble()
    }

    fun getRandomBoolean(): Boolean {
        return Random.nextBoolean()
    }
}