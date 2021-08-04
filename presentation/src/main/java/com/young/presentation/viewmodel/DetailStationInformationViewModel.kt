package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainSubWayTel
import com.young.domain.usecase.info.information.local.LocalGetStationDataUseCase
import com.young.domain.usecase.info.information.remote.RemoteFullRouteInformationUseCase
import com.young.domain.usecase.info.telnumber.RemoteGetSubWayTelUseCase
import com.young.domain.usecase.info.timetable.RemoteTimeTableUseCase
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.mapper.DomainToUiMapper.DomaionToUi
import com.young.presentation.model.AllRouteInformation
import com.young.presentation.model.UiConvenienceInformation
import com.young.presentation.model.UiSubWayTel
import com.young.presentation.model.UiTrailTimeTable
import com.young.presentation.modelfunction.DetailStationInformationViewFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber


class DetailStationInformationViewModel @ViewModelInject constructor(
    private val telUseCase: RemoteGetSubWayTelUseCase,
    private val timeTableUseCase: RemoteTimeTableUseCase,
    private val allInformationUseCase: RemoteFullRouteInformationUseCase ,
    private val stationDataUseCase: LocalGetStationDataUseCase
) : BaseViewModel(), DetailStationInformationViewFunction {

    private val _selectStationData = MutableLiveData<AllRouteInformation>()
    val selectStationData : LiveData<AllRouteInformation>
        get() = _selectStationData

    private val _convenienceInformation = MutableLiveData<UiConvenienceInformation>()
    val convenienceInformation: LiveData<UiConvenienceInformation>
        get() = _convenienceInformation

    private val _timeTableData = MutableLiveData<UiTrailTimeTable>()
    val timeTableData: LiveData<UiTrailTimeTable>
        get() = _timeTableData

    private val _subWayTelData = MutableLiveData<List<UiSubWayTel>>()
    val subWayTelData: LiveData<List<UiSubWayTel>>
        get() = _subWayTelData

    override fun getStationData(stinCodes: List<String>) {
        viewModelScope.launch(handler) {
            stationDataUseCase(stinCodes)
                .map {
                    it.DomainToUi()
                }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e(e)
                }.collect {
                    _selectStationData.value = it.first()
                }
        }
    }

    override fun getTrailTimetables(key: String, railCode: String, dayCd: String, lineCode: String, stationCode: String) {
        viewModelScope.launch(handler) {
            timeTableUseCase.getTrailTimetables(key, railCode, dayCd, lineCode, stationCode)
                .map {
                    it.DomaionToUi()
                }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e(e)
                }
                .onCompletion {
                    setLoadingValue(false)
                }
                .collect {
                    _timeTableData.value = it
                }
        }
    }

    override fun getConvenienceInformation(key: String, lineCode: String, trailCode: String, stationCode: String) {
        viewModelScope.launch(handler) {
            allInformationUseCase.getConvenienceInformation(key, lineCode, trailCode, stationCode)
                .map {
                    it.DomainToUi()
                }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e(e)
                }.onCompletion {
                    setLoadingValue(false)
                }.collect {
                    _convenienceInformation.value = it
                }
        }
    }

    override fun getSubWayTelData(key: String) {
        viewModelScope.launch(handler){
            telUseCase(key)
                .map {
                    BaseMapper.setList(BaseMapper(DomainSubWayTel::class, UiSubWayTel::class))
                        .run { this(it) }
                }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e(e)
                }.onCompletion {
                    setLoadingValue(false)
                }.collect {
                    _subWayTelData.postValue(it)
                }
        }
    }
}