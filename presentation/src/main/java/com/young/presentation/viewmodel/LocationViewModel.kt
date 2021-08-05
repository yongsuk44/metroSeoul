package com.young.presentation.viewmodel

import android.location.Location
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainStationNameAndMapXY
import com.young.domain.model.DomainTrailCodeAndLineCode
import com.young.domain.usecase.info.location.GetLocationUseCase
import com.young.domain.usecase.info.location.LocalStationCoordinateUseCase
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.Event
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.mapper.DomainToUiMapper.DomainToUiDistance
import com.young.presentation.mapper.DomainToUiMapper.UiToDomain
import com.young.presentation.model.UiStationNameAndMapXY
import com.young.presentation.model.UiStationNameDistance
import com.young.presentation.model.UiTrailCodeAndLineCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.IndexOutOfBoundsException

interface LocationViewModelFunction {
    fun setNowLocation(latitude: Double, longitude: Double)
    fun setAllStationNameAndMapXYData(items: List<UiStationNameAndMapXY>)
    fun loadAddressDataSize()
    fun insertAllStationNameAndMapXYData(items: List<UiStationNameAndMapXY>)
    fun getLocationNearStationList()
    fun onStationClick(data : UiStationNameDistance , position: Int)
}

@ExperimentalCoroutinesApi
@FlowPreview
class LocationViewModel @ViewModelInject constructor(
    private val locationUseCase: GetLocationUseCase,
    private val coordinateUseCase: LocalStationCoordinateUseCase
) : BaseViewModel(), LocationViewModelFunction {

    private val _failedLocationData = MutableLiveData<Boolean>()
    val failedLocationData: LiveData<Boolean>
        get() = _failedLocationData

    private val _nowLocationLatitude = MutableLiveData<Double>()
    val nowLocationLatitude: LiveData<Double>
        get() = _nowLocationLatitude

    private val _nowLocationLongitude = MutableLiveData<Double>()
    val nowLocationLongitude: LiveData<Double>
        get() = _nowLocationLongitude

    private val _stationNameAndMapXY = MutableLiveData<List<UiStationNameDistance>>()
    val stationNameAndMapXY: LiveData<List<UiStationNameDistance>>
        get() = _stationNameAndMapXY

    private val _stationCoordinateDataSize = MutableLiveData<Int>()
    val stationCoordinateDataSize: LiveData<Int>
        get() = _stationCoordinateDataSize

    private val _stationClick = MutableLiveData<Event<UiStationNameDistance>>()
    val stationClick: LiveData<Event<UiStationNameDistance>>
        get() = _stationClick

    private val _selectPosition = MutableLiveData<Int>()
    val selectPosition: LiveData<Int>
        get() = _selectPosition

    override fun setNowLocation(latitude: Double, longitude: Double) {
        _nowLocationLatitude.value = latitude
        _nowLocationLongitude.value = longitude

        if (nowLocationLongitude.value != null && nowLocationLatitude.value != null) {
            loadAddressDataSize()
        }
    }

    override fun loadAddressDataSize() {
        viewModelScope.launch(handler) {
            coordinateUseCase.getStationCoordinateDataSize().flowOn(Dispatchers.IO).take(1)
                .collect {
                    _stationCoordinateDataSize.value = it
                }
        }
    }

    override fun setAllStationNameAndMapXYData(items: List<UiStationNameAndMapXY>) {
        insertAllStationNameAndMapXYData(items)
    }

    override fun insertAllStationNameAndMapXYData(items: List<UiStationNameAndMapXY>) {
        viewModelScope.launch(handler) {
            flowOf(coordinateUseCase.insertStationCoordinateData(
                items.map {
                    it.UiToDomain()
                }
            ))
                .flowOn(Dispatchers.IO)
                .collect {
                    getLocationNearStationList()
                }
        }
    }

    override fun getLocationNearStationList() {
        viewModelScope.launch(handler) {
            if (nowLocationLatitude.value != null && nowLocationLongitude.value != null) {

                coordinateUseCase.getLocationNearStationList(nowLocationLatitude.value!!, nowLocationLongitude.value!!, 3.0)
                    .transform {
                        emit(
                            it.DomainToUiDistance(nowLocationLatitude.value!! , nowLocationLongitude.value!!)
                        )
                    }
                    .flowOn(Dispatchers.IO)
                    .catch { e ->
                        Timber.e(e)
                        _failedLocationData.value = true
                    }.onCompletion {
                        setLoadingValue(false)
                    }
                    .collect {
                        _stationNameAndMapXY.value = it
                    }
            }
        }
    }

    override fun onStationClick(data: UiStationNameDistance , position : Int) {
        _selectPosition.value = position
        _stationClick.value = Event(data)
    }
}