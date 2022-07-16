package com.young.metro


import kotlinx.coroutines.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class SuperVisorJobTest {

    @Test
    fun jobTest() = runBlocking {
        val superJob = SupervisorJob()

        CoroutineScope(Dispatchers.IO).launch {
            val firstJob = launch(superJob) {
                throw NullPointerException("First Job Error Check")
            }

            val secondJob = launch(Dispatchers.Default) {
                println("Second Job Check")
            }

            joinAll(firstJob,secondJob)
        }.join()
    }
}