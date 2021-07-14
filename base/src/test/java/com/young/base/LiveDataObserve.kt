package com.young.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

fun <T> LiveData<T>.getAwaitValue(
    time : Long = 2, timeUnit : TimeUnit = TimeUnit.SECONDS
) : T {
    var data : T? = null
    val latch = CountDownLatch(1)

    val observer = object : Observer<T> {
        override fun onChanged(t: T) {
            data = t
            latch.countDown()
            this@getAwaitValue.removeObserver(this)
        }
    }
    
    this.observeForever(observer)
    
    if (!latch.await(time, timeUnit)) throw TimeoutException("Live Data 셋팅 안됨")
    
    return data as T
}