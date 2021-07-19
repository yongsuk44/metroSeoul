package com.young.presentation.consts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    val handler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        _loading.value = false
    }

    private val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean>
        get() = _loading

    fun setLoadingValue(check : Boolean) {
        _loading.value = check
    }
}