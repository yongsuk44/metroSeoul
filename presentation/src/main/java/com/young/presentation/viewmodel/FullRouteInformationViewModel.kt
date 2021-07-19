package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.*
import com.young.domain.usecase.information.remote.RemoteFullRouteInformationUseCase
import com.young.domain.usecase.information.remote.RemoteGetSubWayTelUseCase
import com.young.domain.usecase.information.remote.RemoteTimeTableUseCase
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.mapper.DomainToUiMapper.DomaionToUi
import com.young.presentation.model.UiAllRouteInformation
import com.young.presentation.model.UiSubWayTel
import com.young.presentation.model.UiTrailTimeTable
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

interface FullRouteInformationCase {
    fun loadSubWayTelData(key: String)
    suspend fun getSubWayTelData(key: String)

    fun loadTrailTimeTableData(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String
    )

    suspend fun getTrailTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String
    )

    suspend fun getFullRouteInformation(key: String, lineCode: String)

    fun onChangeTextValue(value : String)
}

class FullRouteInformationViewModel @ViewModelInject constructor(
    private val telUseCase: RemoteGetSubWayTelUseCase,
    private val timeTableUseCase: RemoteTimeTableUseCase,
    private val allInformationUseCase: RemoteFullRouteInformationUseCase
) : BaseViewModel(), FullRouteInformationCase {

    private val _subWayTelData = MutableLiveData<List<UiSubWayTel>>()
    val subWayTelData: LiveData<List<UiSubWayTel>>
        get() = _subWayTelData

    private val _timeTableData = MutableLiveData<UiTrailTimeTable>()
    val timeTableData: LiveData<UiTrailTimeTable>
        get() = _timeTableData

    private val _fullRouteInformation = MutableLiveData<UiAllRouteInformation>()
    val fullRouteInformation: LiveData<UiAllRouteInformation>
        get() = _fullRouteInformation


    override fun loadSubWayTelData(key: String) {
        viewModelScope.launch(handler) {
            getSubWayTelData(key)
        }
    }

    override suspend fun getSubWayTelData(key: String) {
        telUseCase(key)
            .map {
                BaseMapper.setList(BaseMapper(DomainSubWayTel::class, UiSubWayTel::class)).run { this(it) }
            }.catch { e ->
                Timber.e(e)
            }.onCompletion {
                setLoadingValue(true)
            }.collect {
                _subWayTelData.postValue(it)
            }
    }

    override fun loadTrailTimeTableData(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String
    ) {
        viewModelScope.launch(handler) {
//            getTrailTimetables(key, railCode, dayCd, lineCode, stationCode)
            getFullRouteInformation(key, lineCode)
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
                setLoadingValue(true)
            }.collect {
                _timeTableData.value = it
            }
    }

    override suspend fun getFullRouteInformation(key: String, lineCode: String) {
        allInformationUseCase.getFullRouteInformation(key, lineCode)
            .map {
                it.DomainToUi()
            }.catch { e ->
                Timber.e(e)
            }.onCompletion {
                setLoadingValue(true)
            }.collect {
                _fullRouteInformation.value = it
            }
    }

    override fun onChangeTextValue(value: String) {

    }

    fun getTextChangeListener(): FullRouteInformationCase = this
}