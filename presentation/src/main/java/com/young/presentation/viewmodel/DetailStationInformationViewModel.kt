package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.usecase.info.basic.local.LocalGetFullRouteInformationUseCase
import com.young.domain.usecase.info.basic.local.LocalGetStationDataUseCase
import com.young.domain.usecase.info.detail.telnumber.LocalStationTelUseCase
import com.young.domain.usecase.info.detail.telnumber.RemoteGetSubWayTelUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.consts.getApiPath
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.model.IndexAllRouteInformation
import com.young.presentation.model.UiSubWayTel
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
    private val remoteTelUseCase: RemoteGetSubWayTelUseCase,
    private val stationDataUseCase: LocalGetStationDataUseCase,
    private val localFullRouteInformationUseCase: LocalGetFullRouteInformationUseCase,
    private val localTelUseCase: LocalStationTelUseCase
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
                .map {
                    it.map { it.DomainToUi() }
                }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e(e)
                }.onCompletion {
                    setLoadingValue(false)
                    onLinePositionClick(0)
                }.collect {
                    _selectStationLineListData.value = it
                }
        }
    }

    override fun getStationSubData(lineCd : String , stationName : String) {
        viewModelScope.launch(Dispatchers.IO) {

            val seoulFlow = remoteTelUseCase.getSeoulApiTelData(provider.getString(R.string.seoulKey))

            val path = getApiPath(lineCd)
            if (path.isNullOrEmpty()){
                seoulFlow
                    .map {
                        it.find {
                            it.STATION == stationName
                        }?.TELNO_INFO?.replace("[^0-9-]".toRegex(), "")
                            ?: "1544-7788"
                    }
                    .catch {
                        Timber.e(it)
                    }.collect {
                        _stationTelNumber.postValue(it)
                    }
            } else {
                val publicFlow = remoteTelUseCase.getPublicApiTelData(path, provider.getString(R.string.key))
                combineTransform(seoulFlow , publicFlow) { seoul , public ->

                    val seoulList = seoul.map {
                        com.young.domain.model.Data(it.STATION, it.TELNO_INFO.replace("[^0-9-]".toRegex(), ""))
                    }

                    emit(seoulList + public.data)

                }.map {
                    it.find {
                        it.stationName == stationName
                    }?.telNumber ?: "1544-7788"
                }.catch {
                    Timber.e(it)
                }.collect {
                    _stationTelNumber.postValue(it)
                }
            }

//            remoteTelUseCase.getSeoulApiTelData(provider.getString(R.string.seoulKey))
//                .flowOn(Dispatchers.IO)
//                .combineTransform(
//                    remoteTelUseCase.getPublicApiTelData(
//                        getApiPath(lincode = lineCd),
//                        provider.getString(R.string.key)
//                    )
//                ) { seoul, public ->
//                    val seoulList = seoul.map {
//                        com.young.domain.model.Data(
//                            it.STATION,
//                            it.TELNO_INFO.replace("[^0-9-]".toRegex(), "")
//                        )
//                    }
//                    val publicList = public.data
//                    emit(seoulList + publicList)
//                }.flowOn(Dispatchers.IO)
//                .catch { e ->
//                    Timber.e(e)
//                }.collect {
//                    it
//                }
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