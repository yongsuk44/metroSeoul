package com.young.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.Event
import timber.log.Timber

interface PermissionFunction {
    fun setLocationPermissionCheck(check : Boolean)
    fun onLocationSearchClick()
}

class PermissionViewModel : BaseViewModel() , PermissionFunction {

    private val _locationPermission = MutableLiveData<Boolean>()
    val locationPermission : LiveData<Boolean>
        get() = _locationPermission

    private val _locationSearchClick = MutableLiveData<Event<Boolean>>()
    val locationSearchClick : LiveData<Event<Boolean>>
        get() = _locationSearchClick

    override fun setLocationPermissionCheck(check: Boolean) {
        _locationPermission.value = check
    }

    override fun onLocationSearchClick() {
       _locationSearchClick.value = Event(locationPermission.value ?: false)
        Timber.d("현재 위치 근처에 있는 역으로 보여주는 Fragment 화면 이동")
    }
}