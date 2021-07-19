package com.young.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface PermissionFunction {
    fun setLocationPermissionCheck(check : Boolean)
}

class PermissionViewModel : BaseViewModel() , PermissionFunction {

    private val _locationPermission = MutableLiveData<Boolean>()
    val locationPermission : LiveData<Boolean>
        get() = _locationPermission

    override fun setLocationPermissionCheck(check: Boolean) {
        _locationPermission.value = check
    }
}