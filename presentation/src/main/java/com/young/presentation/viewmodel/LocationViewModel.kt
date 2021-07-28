package com.young.presentation.viewmodel

import android.location.Geocoder
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.usecase.info.location.GetLocationUseCase
import com.young.domain.usecase.info.location.LocalStationCoordinateUseCase
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
    suspend fun getStationCoordinateData()
}

@ExperimentalCoroutinesApi
@FlowPreview
class LocationViewModel @ViewModelInject constructor(
    private val locationUseCase: GetLocationUseCase,
    private val coordinateUseCase: LocalStationCoordinateUseCase,
    private val provider: ResourceProvider,
    private val geocoder: Geocoder
) : BaseViewModel(), LocationViewModelFunction {

    private val _locationStringList = MutableLiveData<List<String>>()
    val locationStringList: LiveData<List<String>>
        get() = _locationStringList

    override fun loadAddressData() {
        viewModelScope.launch(handler) {
            Timber.d("start TEST11")
            coordinateUseCase.getStationCoordinateDataSize().flowOn(Dispatchers.IO).take(1)
                .collect {
                    if (it > 0) getStationCoordinateData()
                    else getTrailCodeAndLineCode()
                }
        }
    }

    override suspend fun getTrailCodeAndLineCode() {
        locationUseCase.getTrailCodeAndLineCode()
            .flowOn(Dispatchers.IO)
            .flatMapConcat { responseList ->

                val list = ArrayList<Flow<List<String>>>()

                responseList.forEach { data ->
                    list.add(
                        locationUseCase.getStationAddress(
                            provider.getString(R.string.trailKey),
                            data.railOprIsttCd,
                            data.lnCd
                        )
                    )
                }

                combine(list) {
                    it.reduce { a, b -> a + b }
                }

            }
            .transform {
                it.map {
                    emit(geocoder.getFromLocationName(it, 1))
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


    override suspend fun getStationCoordinateData() {
        TODO("Not yet implemented")
    }
}