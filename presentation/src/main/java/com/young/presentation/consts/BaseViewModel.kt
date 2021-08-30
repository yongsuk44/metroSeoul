package com.young.presentation.consts

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import timber.log.Timber

open class BaseViewModel : ViewModel() {

    val handler = CoroutineExceptionHandler { _, exception ->
        Timber.e(exception)
        _loading.value = false
    }

    private val _loading = MutableLiveData<Boolean>(true)
    val loading: LiveData<Boolean>
        get() = _loading

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String>
        get() = _toastMsg

    fun setLoadingValue(check: Boolean) {
        _loading.value = check
    }

    fun setToastMsg(value: String) {
        _toastMsg.value = value
    }
}

fun <X, Y> CustomTransformationDataMap(liveData: LiveData<X>, block: (X) -> Y): LiveData<Y> {
    return Transformations.map(liveData) {
        val returnData = block(it)
        Timber.d("CustomTransformation After Data : $returnData")
        returnData
    }
}

open class Event<out T>(private val content: T) {
    private var hasHandler = false

    fun getContentIfNotHandler(): T? =
        if (hasHandler) null
        else {
            hasHandler = true
            content
        }
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(t: Event<T>?) {
        t?.getContentIfNotHandler()?.let {
            onEventUnhandledContent(it)
        }
    }
}