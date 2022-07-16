package com.young.presentation.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.FirebaseDatabase
import com.young.domain.usecase.CoordinateUseCase
import com.young.domain.usecase.location.*
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.Event
import com.young.presentation.mapper.DomainToUiMapper.DomainToUiDistance
import com.young.presentation.model.UiStationNameDistance
import com.young.presentation.model.UiUserLocationData
import com.young.presentation.model.UiUserLocationData.Companion.toMapper
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

interface LocationViewModelFunction {
    fun loadStationData(firebase: FirebaseDatabase)
    suspend fun getLocationNearStationList(areaKm: Double, uiUserLocationData: UiUserLocationData): Flow<List<UiStationNameDistance>>
    fun onStationClick(data: UiStationNameDistance, position: Int)
    fun onLocationRadiusData(data: Double)
}

const val LOCATION_RADIUS = "locationRadius"

@ExperimentalCoroutinesApi
@FlowPreview
class LocationViewModel @ViewModelInject constructor(
    @Assisted private val saveInstance: SavedStateHandle,
    private val useCase: CoordinateUseCase,
    private val ioDispatcher: CoroutineDispatcher,
    private val updateLocationServiceUseCase: UpdateLocationServiceBaseUseCase,
    private val readLocationServiceUseCase: ReadLocationServiceBaseUseCase,
    private val getStationCoordinateDataUseCase: GetStationCoordinateDataBaseUseCase
) : BaseViewModel(), LocationViewModelFunction {

    private val _locationRadiusData = MutableStateFlow(saveInstance.get(LOCATION_RADIUS) ?: 3.0)
    val locationRadiusData: StateFlow<Double>
        get() = _locationRadiusData

    private val _zeroLocationDataList = MutableLiveData<Boolean>(false)
    val zeroLocationDataList: LiveData<Boolean>
        get() = _zeroLocationDataList

    private val _failedLocationData = MutableLiveData<Boolean>()
    val failedLocationData: LiveData<Boolean>
        get() = _failedLocationData

    private val _stationNameAndMapXY = MutableLiveData<List<UiStationNameDistance>>()
    val stationNameAndMapXY: LiveData<List<UiStationNameDistance>>
        get() = _stationNameAndMapXY

    private val _stationClick = MutableLiveData<Event<UiStationNameDistance>>()
    val stationClick: LiveData<Event<UiStationNameDistance>>
        get() = _stationClick

    private val _selectPosition = MutableLiveData<Int>()
    val selectPosition: LiveData<Int>
        get() = _selectPosition

    fun updateLocationService() = updateLocationServiceUseCase.updateLocationService()

    override fun onStationClick(data: UiStationNameDistance, position: Int) {
        _selectPosition.value = position
        _stationClick.value = Event(data)
    }

    override fun onLocationRadiusData(data: Double) {
        saveInstance.set(LOCATION_RADIUS, data)
        viewModelScope.launch(ioDispatcher) { _locationRadiusData.emit(data) }
    }

    private fun locationRadiusChange() {
        viewModelScope.launch(ioDispatcher) {
            locationRadiusData.collect { radius ->
                readLocationServiceUseCase.readLocationService()
                    .map { it.toMapper() }
                    .flatMapConcat { uiUserLocationData ->
                        getLocationNearStationList(radius , uiUserLocationData)
                    }
                    .catch { e ->
                        Timber.e(e)
                        setLoadingValue(false)
                        _failedLocationData.value = true
                    }.collect {
                        _zeroLocationDataList.value = it.isEmpty()
                        _stationNameAndMapXY.value = it
                    }
            }
        }
    }

    override fun loadStationData(firebase: FirebaseDatabase) {
        viewModelScope.launch(handler + ioDispatcher) {
            useCase.getStationCoordinateDataSize()
                .take(1)
                .flatMapConcat { size ->
                    if (size <= 0) getStationCoordinateDataUseCase.insertStationCoordinateData(firebase)
                    else flowOf(true)
                }
                .flatMapConcat { verify ->
                    if (verify) readLocationServiceUseCase.readLocationService()
                    else throw NullPointerException("Station Data Error")
                }
                .map { it.toMapper() }
                .flatMapConcat { uiUserLocationData ->
                    getLocationNearStationList(locationRadiusData.value ?: 3.0, uiUserLocationData)
                }
                .catch { e ->
                    Timber.e(e)
                    setLoadingValue(false)
                    _failedLocationData.value = true
                }.collect {
                    _zeroLocationDataList.value = it.isEmpty()
                    _stationNameAndMapXY.value = it
                }
        }

        locationRadiusChange()
    }

    override suspend fun getLocationNearStationList(areaKm: Double, uiUserLocationData: UiUserLocationData) =
        useCase.getLocationNearStationList(uiUserLocationData.latitude, uiUserLocationData.longitude, areaKm)
            .transform { emit(it.DomainToUiDistance(uiUserLocationData.latitude, uiUserLocationData.longitude)) }
            .flowOn(Dispatchers.Default)

}