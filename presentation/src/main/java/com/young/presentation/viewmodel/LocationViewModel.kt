package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.usecase.info.location.GetLocationUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.ResourceProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

interface LocationViewModelFunction {
    fun loadAddressData()
    suspend fun getTrailCodeAndLineCode()
}

@ExperimentalCoroutinesApi
@FlowPreview
class LocationViewModel @ViewModelInject constructor(
    private val locationUseCase: GetLocationUseCase,
    private val provider: ResourceProvider
) : BaseViewModel(), LocationViewModelFunction {

    private val _locationStringList = MutableLiveData<List<String>>()
    val locationStringList: LiveData<List<String>>
        get() = _locationStringList

    override fun loadAddressData() {
        viewModelScope.launch(handler) {
            Timber.d("start TEST11")
            getTrailCodeAndLineCode()
        }
    }


    override suspend fun getTrailCodeAndLineCode() {
        locationUseCase.getTrailCodeAndLineCode()
            .flowOn(Dispatchers.IO)
            .flatMapConcat {

                val list = ArrayList<Flow<List<String>>>()

                it.forEach {
                    list.add(locationUseCase.getStationAddress(provider.getString(R.string.trailKey), it.railOprIsttCd, it.lnCd))
                }

                combine(list) {
                    it.reduce { a, b -> a + b }
                }
            }
            .flowOn(Dispatchers.Default)
            .catch { e ->
                Timber.e(e)
            }.onCompletion {
                setLoadingValue(false)
            }.collect {
                _locationStringList.value = it
            }
    }
}