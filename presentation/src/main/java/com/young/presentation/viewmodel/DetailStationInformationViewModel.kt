package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainSubWayTel
import com.young.domain.usecase.info.information.RemoteFullRouteInformationUseCase
import com.young.domain.usecase.info.timetable.RemoteTimeTableUseCase
import com.young.domain.usecase.info.telnumber.RemoteGetSubWayTelUseCase
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.mapper.DomainToUiMapper.DomaionToUi
import com.young.presentation.model.UiConvenienceInformation
import com.young.presentation.model.UiSubWayTel
import com.young.presentation.model.UiTrailTimeTable
import com.young.presentation.modelfunction.DetailStationInformationViewFunction
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import timber.log.Timber


class DetailStationInformationViewModel @ViewModelInject constructor(
    private val telUseCase: RemoteGetSubWayTelUseCase,
    private val timeTableUseCase: RemoteTimeTableUseCase,
    private val allInformationUseCase: RemoteFullRouteInformationUseCase
) : BaseViewModel(), DetailStationInformationViewFunction {

    private val _convenienceInformation = MutableLiveData<UiConvenienceInformation>()
    val convenienceInformation: LiveData<UiConvenienceInformation>
        get() = _convenienceInformation

    private val _timeTableData = MutableLiveData<UiTrailTimeTable>()
    val timeTableData: LiveData<UiTrailTimeTable>
        get() = _timeTableData

    private val _subWayTelData = MutableLiveData<List<UiSubWayTel>>()
    val subWayTelData: LiveData<List<UiSubWayTel>>
        get() = _subWayTelData

    override fun loadTrailTimeTableData(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String
    ) {
        viewModelScope.launch(handler) {
            getTrailTimetables(key, railCode, dayCd, lineCode, stationCode)
        }
    }

    override suspend fun getTrailTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String
    ) {
        timeTableUseCase.getTrailTimetables(key, railCode, dayCd, lineCode, stationCode)
            .map {
                it.DomaionToUi()
            }.catch { e ->
                Timber.e(e)
            }.onCompletion {
                setLoadingValue(false)
            }.collect {
                _timeTableData.value = it
            }
    }

    override suspend fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    ) {
        allInformationUseCase.getConvenienceInformation(key, lineCode, trailCode, stationCode)
            .map {
                it.DomainToUi()
            }.catch { e ->
                Timber.e(e)
            }.onCompletion {
                setLoadingValue(false)
            }.collect {
                _convenienceInformation.value = it
            }
    }

    override fun loadSubWayTelData(key: String) {
        viewModelScope.launch(handler) {
            getSubWayTelData(key)
        }
    }

    override suspend fun getSubWayTelData(key: String) {
        telUseCase(key)
            .map {
                BaseMapper.setList(BaseMapper(DomainSubWayTel::class, UiSubWayTel::class))
                    .run { this(it) }
            }.catch { e ->
                Timber.e(e)
            }.onCompletion {
                setLoadingValue(false)
            }.collect {
                _subWayTelData.postValue(it)
            }
    }
}