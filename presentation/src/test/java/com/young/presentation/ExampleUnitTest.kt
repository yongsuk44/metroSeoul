package com.young.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.young.presentation.consts.CustomTransformationDataMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {
    @get:Rule
    val rule = TestCoroutineRule()

    lateinit var testLiveData : MutableLiveData<String>
    lateinit var testData : LiveData<Int>

    @Before
    fun setUp() {
        testLiveData.value = "2345"

        testData = CustomTransformationDataMap(testLiveData) {
            it.toInt() - 100
        }
    }

    @Test
    fun test() {
        runBlocking {
            println(testData.getAwaitValue())
        }
    }
}