package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainPlatformEntrance
import com.young.domain.model.DomainSubWayTel
import com.young.domain.usecase.info.basic.local.LocalGetStationDataUseCase
import com.young.domain.usecase.info.basic.remote.RemoteFullRouteInformationUseCase
import com.young.domain.usecase.info.detail.platformentrance.PlatformEntranceUseCase
import com.young.domain.usecase.info.detail.telnumber.RemoteGetSubWayTelUseCase
import com.young.domain.usecase.info.detail.timetable.RemoteTimeTableUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.mapper.DomainToUiMapper.DomaionToUi
import com.young.presentation.model.*
import com.young.presentation.modelfunction.DetailStationInformationViewFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
class DetailStationInformationViewModel @ViewModelInject constructor(
    private val provider: ResourceProvider,
    private val telUseCase: RemoteGetSubWayTelUseCase,
    private val stationDataUseCase: LocalGetStationDataUseCase
) : BaseViewModel(), DetailStationInformationViewFunction {

    private val _selectStationData = MutableLiveData<List<IndexAllRouteInformation>>()
    val selectStationData : LiveData<List<IndexAllRouteInformation>>
        get() = _selectStationData

    private val _selectLineCodePositionChange = MutableLiveData<Int>(0)
    val selectLineCodePositionChange : LiveData<Int>
        get() = _selectLineCodePositionChange

    private val _subWayTelData = MutableLiveData<List<UiSubWayTel>>()
    val subWayTelData: LiveData<List<UiSubWayTel>>
        get() = _subWayTelData

    override fun getStationData(stinCodes: List<String>) {
        viewModelScope.launch(handler) {
            stationDataUseCase(stinCodes)
                .map {
                    it.map { it.DomainToUi() }
                }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e(e)
                }.collect {
                    _selectStationData.value = it
                }
        }
    }

    override fun getSubWayTelData() {
        viewModelScope.launch(handler){
            telUseCase(provider.getString(R.string.trailKey))
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
                    _subWayTelData.value = it
                }
        }
    }

    override fun onLinePositionClick(position: Int) {
        _selectLineCodePositionChange.value = position
    }
}