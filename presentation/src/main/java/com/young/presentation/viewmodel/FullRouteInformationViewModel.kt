package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainSubWayTel
import com.young.domain.usecase.information.remote.RemoteFullRouteInformationUseCase
import com.young.domain.usecase.information.remote.RemoteGetSubWayTelUseCase
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.Event
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.model.UiAllRouteInformation
import com.young.presentation.model.UiSubWayTel
import com.young.presentation.modelfunction.CustomTextWatcher
import com.young.presentation.modelfunction.FullRouteInformationCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import timber.log.Timber

class FullRouteInformationViewModel @ViewModelInject constructor(
    private val telUseCase: RemoteGetSubWayTelUseCase,
    private val allInformationUseCase: RemoteFullRouteInformationUseCase
) : BaseViewModel(), FullRouteInformationCase {

    private val _subWayTelData = MutableLiveData<List<UiSubWayTel>>()
    val subWayTelData: LiveData<List<UiSubWayTel>>
        get() = _subWayTelData

    private val _fullRouteInformation = MutableLiveData<UiAllRouteInformation>()
    val fullRouteInformation: LiveData<UiAllRouteInformation>
        get() = _fullRouteInformation

    val _userSearchStationName = MutableLiveData<String>()
    val userSearchStationName : LiveData<String>
        get() = _userSearchStationName

    private val _searchActionStation = MutableLiveData<Event<String>>()
    val searchActionStation : LiveData<Event<String>>
        get() = _searchActionStation

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
                setLoadingValue(true)
            }.collect {
                _subWayTelData.postValue(it)
            }
    }

    override fun loadFullRouteInformation(key: String, lineCode: String) {
        viewModelScope.launch(handler) {
            getFullRouteInformation(key, lineCode)
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
}