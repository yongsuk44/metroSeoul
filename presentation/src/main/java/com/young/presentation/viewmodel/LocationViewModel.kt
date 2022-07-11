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
import com.young.domain.usecase.location.UpdateLocationServiceUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.Event
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.mapper.DomainToUiMapper.DomainToUiDistance
import com.young.presentation.mapper.DomainToUiMapper.UiToDomain
import com.young.presentation.model.UiStationNameAndMapXY
import com.young.presentation.model.UiStationNameDistance
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

interface LocationViewModelFunction {
    fun setNowLocation(latitude: Double?, longitude: Double?)
    fun loadAddressDataSize()
    fun insertAllStationNameAndMapXYData(items: List<UiStationNameAndMapXY>)
    fun getLocationNearStationList(areaKm: Double)
    fun onStationClick(data: UiStationNameDistance, position: Int)
    fun getFireBaseMapXYData(firebase: FirebaseDatabase)
    fun onLocationRadiusData(data: Double)
    fun onSaveLocationRadiusData(data: Double)
}

@ExperimentalCoroutinesApi
@FlowPreview
class LocationViewModel @ViewModelInject constructor(
    private val provider: ResourceProvider,
    private val useCase: CoordinateUseCase,
    @Assisted private val saveInstance: SavedStateHandle,
    private val updateLocationServiceUseCase: UpdateLocationServiceUseCase
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

    fun updateLocationService() = updateLocationServiceUseCase()

    override fun onSaveLocationRadiusData(data: Double) {
        saveInstance.set("locationRadius", data)
    }

    override fun setNowLocation(latitude: Double?, longitude: Double?) {
        _nowLocationLatitude.value = latitude ?: return setToastMsg(provider.getString(R.string.toast_location_data_failed))
        _nowLocationLongitude.value = longitude ?: return setToastMsg(provider.getString(R.string.toast_location_data_failed))

        loadAddressDataSize()
    }

    override fun loadAddressDataSize() {
        viewModelScope.launch(handler) {
            useCase.getStationCoordinateDataSize()
                .flowOn(Dispatchers.IO)
                .stateIn(this)
                .take(1)
                .collect { _stationCoordinateDataSize.value = it }
        }
    }

    override fun getFireBaseMapXYData(firebase: FirebaseDatabase) {
        viewModelScope.launch(handler) {
            withContext(Dispatchers.IO) {
                firebase.reference.child("StationLocationData").get()
                    .addOnSuccessListener { snapShot ->
                        launch(Dispatchers.Default) {
                            val uiStationNameAndMapXYList = (snapShot.value as List<String>).map { location -> Gson().fromJson(location, UiStationNameAndMapXY::class.java) }
                            insertAllStationNameAndMapXYData(uiStationNameAndMapXYList)
                        }
                    }
                    .addOnCanceledListener {
                        setToastMsg(provider.getString(R.string.toast_location_data_cancel))
                    }
                    .addOnFailureListener { exception ->
                        Timber.e(exception)
                        setToastMsg(exception.message ?: provider.getString(R.string.toast_location_data_failed))
                    }
            }
        }
    }

    override fun insertAllStationNameAndMapXYData(items: List<UiStationNameAndMapXY>) {
        viewModelScope.launch(handler) {
            flowOf(items)
                .map { it.map { it.UiToDomain() } }
                .flatMapConcat { useCase.insertStationCoordinateData(it) }
                .flowOn(Dispatchers.Default)
                .collect { _locationRadiusData.value = 3.0 }
        }
    }

    override fun getLocationNearStationList(areaKm: Double) {
        viewModelScope.launch {
            if (nowLocationLatitude.value != null && nowLocationLongitude.value != null) {
                useCase.getLocationNearStationList(
                    nowLocationLatitude.value!!,
                    nowLocationLongitude.value!!,
                    areaKm
                )
                    .flowOn(Dispatchers.IO)
                    .transform {
                        emit(
                            it.DomainToUiDistance(
                                nowLocationLatitude.value!!,
                                nowLocationLongitude.value!!
                            )
                        )
                    }
                    .flowOn(Dispatchers.Default)
                    .catch { e ->
                        Timber.e(e)
                        setLoadingValue(false)
                        _failedLocationData.value = true
                    }.collect {
                        _zeroLocationDataList.value = it.isEmpty()
                        _stationNameAndMapXY.value = it
                    }
            } else {
                provider.getString(R.string.toast_location_data_failed)
            }
        }
    }

    override fun onStationClick(data: UiStationNameDistance, position: Int) {
        _selectPosition.value = position
        _stationClick.value = Event(data)
    }

    override fun onLocationRadiusData(data: Double) {
        _locationRadiusData.value = data
    }
}