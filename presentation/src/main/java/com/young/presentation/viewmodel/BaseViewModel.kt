package com.young.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel : ViewModel() {

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("test" , exception.message.toString())
        _loading.value = false
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean>
        get() = _loading

    fun setLoadingValue(check : Boolean) {
        _loading.value = check
    }
}