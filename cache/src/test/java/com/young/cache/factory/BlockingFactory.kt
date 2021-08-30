package com.young.cache.factory

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


fun runBlockIOTest(block: suspend () -> Unit) {
    runBlocking(Dispatchers.IO) {
        block()
    }
}

fun runBlockMainTest(block: suspend () -> Unit) {
    runBlocking(Dispatchers.Main) {
        block()
    }
}

