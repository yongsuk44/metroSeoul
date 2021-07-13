package com.kaii.coroutinetest.factory

import com.kaii.coroutinetest.mapper.Birth
import com.kaii.coroutinetest.mapper.ChildrenA
import com.kaii.coroutinetest.mapper.Name
import com.kaii.coroutinetest.mapper.TestA
import java.util.concurrent.ThreadLocalRandom

class DataFactory {
    companion object Factory {
        fun randomString(): String {
            return java.util.UUID.randomUUID().toString()
        }

        fun randomInt(): Int {
            return ThreadLocalRandom.current().nextInt(0, 1000 + 1)
        }

        fun randomDouble() : Double {
            return ThreadLocalRandom.current().nextDouble(0.0 , (1000.0))
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

        fun makeBirth() : Birth {
            return Birth(
                randomInt() ,
                randomInt()
            )
        }

        fun makeName() : Name {
            return Name(
                randomString() ,
                randomString()
            )
        }

        fun makeTestA() : TestA {
            return TestA(
                makeBirth() ,
                makeName() ,
                randomDouble()
            )
        }

        fun makeListTestA(count : Int) : List<TestA> {
            return MutableList(count) {
                makeTestA()
            }
        }

        fun makeChildrenA(count: Int) : ChildrenA {
            return ChildrenA(
                makeListTestA(count) ,
                count
            )
        }
    }
}