package com.young.remote.api

import kotlinx.coroutines.ExperimentalCoroutinesApi
import okio.buffer
import okio.source
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.nio.charset.StandardCharsets

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class JsonTest {

    @Test
    fun `Protocol Buffer Test`() {
        val inputStream = javaClass.classLoader?.getResourceAsStream("test")
        val source = inputStream?.let { inputStream.source().buffer() }
        val body = source?.readString(StandardCharsets.UTF_8)

        body?.toCharArray()?.forEach {
            println(it to it.hashCode())
        }
    }
}