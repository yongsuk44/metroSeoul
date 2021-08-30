package com.young.presentation.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
import com.young.domain.usecase.CoordinateUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.Event
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.mapper.DomainToUiMapper.DomainToUiDistance
import com.young.presentation.mapper.DomainToUiMapper.UiToDomain
import com.young.presentation.model.UiStationNameAndMapXY
import com.young.presentation.model.UiStationNameDistance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

interface LocationViewModelFunction {
    fun setNowLocation(latitude: Double, longitude: Double)
    fun loadAddressDataSize()
    fun insertAllStationNameAndMapXYData(items: List<UiStationNameAndMapXY>)
    fun getLocationNearStationList(areaKm : Double)
    fun onStationClick(data : UiStationNameDistance , position: Int)
    fun getFireBaseMapXYData(firebase : FirebaseDatabase)
    fun onLocationRadiusData(data : Double)
    fun onSaveLocationRadiusData(data : Double)
}

@ExperimentalCoroutinesApi
@FlowPreview
class LocationViewModel @ViewModelInject constructor(
    private val provider : ResourceProvider,
    private val useCase : CoordinateUseCase,
    @Assisted private val saveInstance : SavedStateHandle
) : BaseViewModel(), LocationViewModelFunction {

    private val _locationRadiusData = MutableLiveData<Double>(saveInstance.get("locationRadius"))
    val locationRadiusData: LiveData<Double>
        get() = _locationRadiusData

    private val _zeroLocationDataList = MutableLiveData<Boolean>(false)
    val zeroLocationDataList: LiveData<Boolean>
        get() = _zeroLocationDataList

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

    override fun onSaveLocationRadiusData(data: Double) {
        saveInstance.set("locationRadius" , data)
    }

    override fun setNowLocation(latitude: Double, longitude: Double) {
        _nowLocationLatitude.value = latitude
        _nowLocationLongitude.value = longitude

        if (nowLocationLongitude.value != null && nowLocationLatitude.value != null) {
            loadAddressDataSize()
        }
    }

    override fun loadAddressDataSize() {
        viewModelScope.launch(handler) {
            useCase.getStationCoordinateDataSize()
                .flowOn(Dispatchers.IO)
                .take(1)
                .collect {
                    _stationCoordinateDataSize.value = it
                }
        }
    }

    override fun getFireBaseMapXYData(firebase: FirebaseDatabase) {
        viewModelScope.launch {
            flowOf(firebase.reference.child("StationLocationData").get())
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e(e)
                    setToastMsg(provider.getString(R.string.toast_location_data_failed))
                }
                .collect {
                    it.addOnSuccessListener {
                        insertAllStationNameAndMapXYData(
                            (it.value as List<String>).map {
                                Gson().fromJson(it, UiStationNameAndMapXY::class.java)
                            }
                        )
                    }.addOnCanceledListener {
                        setToastMsg(provider.getString(R.string.toast_location_data_cancel))
                    }
                }
        }
    }

    override fun insertAllStationNameAndMapXYData(items: List<UiStationNameAndMapXY>) {
        viewModelScope.launch(handler) {
            flowOf(items)
                .map {
                    it.map { it.UiToDomain() }
                }
                .flatMapConcat {
                    flowOf(useCase.insertStationCoordinateData(it)).flowOn(Dispatchers.IO)
                }
                .collect {
                    _locationRadiusData.value = 3.0
                }
        }
    }

    override fun getLocationNearStationList(areaKm: Double) {
        viewModelScope.launch {
            if (nowLocationLatitude.value != null && nowLocationLongitude.value != null) {
                useCase.getLocationNearStationList(nowLocationLatitude.value!!, nowLocationLongitude.value!!, areaKm)
                    .flowOn(Dispatchers.IO)
                    .transform {
                        emit(
                            it.DomainToUiDistance(nowLocationLatitude.value!! , nowLocationLongitude.value!!)
                        )
                    }
                    .flowOn(Dispatchers.Default)
                    .catch { e ->
                        Timber.e(e)
                        _failedLocationData.value = true
                    }.onCompletion {
                        setLoadingValue(false)
                    }
                    .collect {
                        _zeroLocationDataList.value = it.isEmpty()
                        _stationNameAndMapXY.value = it
                    }
            }
        }
    }

    override fun onStationClick(data: UiStationNameDistance , position : Int) {
        _selectPosition.value = position
        _stationClick.value = Event(data)
    }

    override fun onLocationRadiusData(data: Double) {
        _locationRadiusData.value = data
    }
}