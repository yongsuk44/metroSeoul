package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.usecase.local.LocalAllStationCodeUseCase
import com.young.domain.usecase.local.LocalGetStationDataUseCase
import com.young.domain.usecase.remote.RemoteStationTelUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.model.IndexAllRouteInformation
import com.young.presentation.model.Row
import com.young.presentation.model.StationItem
import com.young.presentation.model.UiSubWayTel
import com.young.presentation.modelfunction.DetailStationInformationViewFunction
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.lang.NullPointerException

@FlowPreview
@ExperimentalCoroutinesApi
class DetailStationInformationViewModel @ViewModelInject constructor(
    private val provider: ResourceProvider,
    private val remoteTelUseCase: RemoteStationTelUseCase,
    private val localAllStationCodeUseCase: LocalAllStationCodeUseCase,
    private val stationDataUseCase: LocalGetStationDataUseCase
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
            stationDataUseCase(stinCodes)
                .flowOn(Dispatchers.IO)
                .map {
                    it.map { it.DomainToUi() }
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    _selectStationLineListData.value = it
                    onLinePositionClick(0)
                }
        }
    }

    override fun getStationCodeToTelData(stationCode : String) {
        viewModelScope.launch(Dispatchers.IO) {
            localAllStationCodeUseCase.findStationCode(stationCode)
                .flatMapConcat {
                    if (it == null) throw NullPointerException("Station Code를 찾지 못함")
                    else remoteTelUseCase.getStationTelData(provider.getString(R.string.key), it.STATION_CD)
                }
                .transform {
                    emit(it.response.body?.items)
                }.catch {
                    _stationTelNumber.postValue("1544-7788")
                    Timber.e(it)
                }.collect {
                    withContext(Dispatchers.Main) {
                        _stationTelNumber.value = it?.first()?.phoneNumber ?: "1544-7788"
                        setLoadingValue(false)
                    }
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