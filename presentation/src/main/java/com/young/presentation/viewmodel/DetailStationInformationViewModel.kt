package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.StationDataUseCase
import com.young.domain.usecase.information.ReadRouteInformationBaseUseCase
import com.young.domain.usecase.information.ReadRouteInformationUseCase
import com.young.domain.usecase.information.ReadStationTelNumberBaseUseCase
import com.young.domain.usecase.information.ReadStationTelNumberUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.model.IndexAllRouteInformation
import com.young.presentation.model.UiSubWayTel
import com.young.presentation.modelfunction.DetailStationInformationViewFunction
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class DetailStationInformationViewModel @ViewModelInject constructor(
    private val readStationTelNumberUseCase: ReadStationTelNumberBaseUseCase,
    private val readRouteInformationUseCase: ReadRouteInformationBaseUseCase
) : BaseViewModel(), DetailStationInformationViewFunction {

    private val _selectStationLineListData = MutableLiveData<List<IndexAllRouteInformation>>()
    val selectStationLineListData: LiveData<List<IndexAllRouteInformation>>
        get() = _selectStationLineListData

    private val _selectStationLineData = MutableLiveData<IndexAllRouteInformation>()
    val selectStationLineData: LiveData<IndexAllRouteInformation>
        get() = _selectStationLineData

    private val _selectLineCodePositionChange = MutableLiveData<Int>()
    val selectLineCodePositionChange: LiveData<Int>
        get() = _selectLineCodePositionChange

    private val _stationSubData = MutableLiveData<List<UiSubWayTel>>()
    val stationSubData: LiveData<List<UiSubWayTel>>
        get() = _stationSubData

    private val _stationTelNumber = MutableLiveData<String>()
    val stationTelNumber: LiveData<String>
        get() = _stationTelNumber

    private val _stationTelClick = MutableLiveData<String>()
    val stationTelClick: LiveData<String>
        get() = _stationTelClick

    override fun getStationData(stinCodes: List<String>) {
        viewModelScope.launch(handler) {
            readRouteInformationUseCase.readRouteInformation(stinCodes)
                .map { it.map { it.DomainToUi() } }
                .onCompletion { onLinePositionClick(0) }
                .flowOn(Dispatchers.IO)
                .collect { _selectStationLineListData.value = it }
        }
    }

    override fun getStationCodeToTelData(stationCode : String, key: String) {
        viewModelScope.launch {
            readStationTelNumberUseCase.readStationTelNumber(stationCode, key)
                .flowOn(Dispatchers.IO)
                .onStart { setLoadingValue(true) }
                .onCompletion { setLoadingValue(false) }
                .catch {
                    _stationTelNumber.postValue("1544-7788")
                    Timber.e(it)
                }.collect { telNumber ->
                    _stationTelNumber.value = telNumber
                }
        }
    }

    override fun onLinePositionClick(position: Int) {
        _selectStationLineData.value = _selectStationLineListData.value?.get(position)
        _selectLineCodePositionChange.value = position
    }

    override fun onStationCallClick() {
        _stationTelClick.value = stationTelNumber.value ?: "1544-7788"
    }
}