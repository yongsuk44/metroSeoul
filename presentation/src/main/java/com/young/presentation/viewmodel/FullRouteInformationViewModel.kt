package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.usecase.information.local.LocalGetFullRouteInformationUseCase
import com.young.domain.usecase.information.local.LocalInsertFullRouteInformationUseCase
import com.young.domain.usecase.information.remote.RemoteFullRouteInformationUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.Event
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.model.AllRouteInformation
import com.young.presentation.modelfunction.FullRouteInformationCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class FullRouteInformationViewModel @ViewModelInject constructor(
    private val allInformationUseCase: RemoteFullRouteInformationUseCase,
    private val localInsertUseCase : LocalInsertFullRouteInformationUseCase,
    private val localGetUseCase : LocalGetFullRouteInformationUseCase,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(), FullRouteInformationCase {

    private val _fullRouteInformation = MutableLiveData<List<AllRouteInformation>>()
    val fullRouteInformation: LiveData<List<AllRouteInformation>>
        get() = _fullRouteInformation

    val _userSearchStationName = MutableLiveData<String>()
    val userSearchStationName : LiveData<String>
        get() = _userSearchStationName

    private val _searchActionStation = MutableLiveData<Event<AllRouteInformation>>()
    val searchActionStation : LiveData<Event<AllRouteInformation>>
        get() = _searchActionStation

    private val _searchEditViewClick = MutableLiveData<Event<Boolean>>()
    val searchEditViewClick : LiveData<Event<Boolean>>
        get() = _searchEditViewClick

    override fun loadFullRouteInformation() {
        viewModelScope.launch(handler) {
            if (localGetUseCase.getDataSize() > 0) getLocalFullRouteInformation()
            else getFullRouteInformation(resourceProvider.getString(R.string.trailKey))
        }
    }

    override suspend fun getFullRouteInformation(key: String) {
        allInformationUseCase.getFullRouteInformation(key)
            .catch { e ->
                Timber.e(e)
            }.onCompletion {
                setLoadingValue(false)
            }.collectLatest {
                localInsertUseCase.invoke(it.body)
                getLocalFullRouteInformation()
            }
    }

    override suspend fun getLocalFullRouteInformation() {
        localGetUseCase.invoke(Unit)
            .map {
                it.DomainToUi()
            }.catch { e->
                Timber.e(e)
            }.onCompletion {
                setLoadingValue(false)
            }.collect {
                _fullRouteInformation.value = it
            }
    }

    override fun onSearchEditViewClick(value : Boolean) {
        _searchEditViewClick.value = Event(value)
    }

    override fun onStationClick(item: AllRouteInformation) {
        _searchActionStation.value = Event(item)
    }

    override fun onSearchEditViewClear() {
        _userSearchStationName.value = ""
    }
}