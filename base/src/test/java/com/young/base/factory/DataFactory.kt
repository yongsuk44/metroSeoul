package com.young.base.factory

import java.util.concurrent.ThreadLocalRandom

object DataFactory {

    fun randomString(): String {
        return java.util.UUID.randomUUID().toString()
    }

    fun randomInt(): Int {
        return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
    }

    fun randomDouble(): Double {
        return ThreadLocalRandom.current().nextDouble(0.0, (1000.0))
    }

    fun randomLong(): Long {
        return randomInt().toLong()
    }

    fun randomBoolean(): Boolean {
        return Math.random() < 0.5
    }

    fun makeStringList(count: Int): List<String> {
        val items: MutableList<String> = mutableListOf()
        repeat(count) {
            items.add(randomString())
        }
        return items
    }

    fun makeIntList(count: Int): List<Int> {
        val items: MutableList<Int> = mutableListOf()
        repeat(count) {
            items.add(randomInt())
        }
        return items
    }
}