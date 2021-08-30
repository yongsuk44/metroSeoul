package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.usecase.cache.LocalGetFullRouteInformationUseCase
import com.young.domain.usecase.cache.LocalInsertFullRouteInformationUseCase
import com.young.domain.usecase.remote.RemoteFullRouteInformationUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.Event
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.model.ListRouteInformation
import com.young.presentation.modelfunction.FullRouteInformationCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

@FlowPreview
class FullRouteInformationViewModel @ViewModelInject constructor(
    private val allInformationUseCase: RemoteFullRouteInformationUseCase,
    private val localInsertUseCase: LocalInsertFullRouteInformationUseCase,
    private val localGetUseCase: LocalGetFullRouteInformationUseCase,
    private val localAllStationCodeUseCase: LocalAllStationCodeUseCase,
    private val resourceProvider: ResourceProvider
) : BaseViewModel(), FullRouteInformationCase {

    private val _failedInformationData = MutableLiveData<Boolean>(false)
    val failedInformationData: LiveData<Boolean>
        get() = _failedInformationData

    private val _fullRouteInformation = MutableLiveData<List<ListRouteInformation>>()
    val fullRouteInformation: LiveData<List<ListRouteInformation>>
        get() = _fullRouteInformation

    val _userSearchStationName = MutableLiveData<String>()
    val userSearchStationName: LiveData<String>
        get() = _userSearchStationName

    private val _searchActionStation = MutableLiveData<Event<ListRouteInformation>>()
    val searchActionStation: LiveData<Event<ListRouteInformation>>
        get() = _searchActionStation

    private val _searchEditViewClick = MutableLiveData<Event<Boolean>>()
    val searchEditViewClick: LiveData<Event<Boolean>>
        get() = _searchEditViewClick

    private val _selectPosition = MutableLiveData<Int>()
    val selectPosition: LiveData<Int>

        get() = _selectPosition


    override fun loadFullRouteInformation() {
        viewModelScope.launch(handler) {
            localGetUseCase.getDataSize().take(1).flowOn(Dispatchers.IO).collect { size ->
                if (size > 0)
                    getLocalFullRouteInformation()
                else
                    getFullRouteInformation(resourceProvider.getString(R.string.trailKey))
            }
        }
    }

    override suspend fun getFullRouteInformation(key: String) {
        allInformationUseCase.getFullRouteInformation(key)
            .flatMapConcat {
                localInsertUseCase.invoke(it.body)
            }
            .flowOn(Dispatchers.IO)
            .catch { e ->
                Timber.e(e)
                _failedInformationData.value = true
            }.collect {
                getLocalFullRouteInformation()
            }
    }

    override suspend fun getLocalFullRouteInformation() {
        localGetUseCase.getAllData()
            .map {
                it.DomainToUi()
            }
            .flowOn(Dispatchers.IO)
            .catch { e ->
                Timber.e(e)
                _failedInformationData.value = true
            }.collect {
                _fullRouteInformation.value = it
            }
    }

    override fun insertAllStationCodes() {
        viewModelScope.launch {
            allInformationUseCase.getAllStationCode(resourceProvider.getString(R.string.seoulKey))
                .flowOn(Dispatchers.IO)
                .flatMapConcat {
                    flowOf(localAllStationCodeUseCase.insert(it))
                }
                .flowOn(Dispatchers.IO)
                .collect()
        }

    }

    override fun onSearchEditViewClick(value: Boolean) {
        _searchEditViewClick.value = Event(value)
    }

    override fun onStationClick(item: ListRouteInformation , position : Int) {
        _selectPosition.value = position
        _searchActionStation.value = Event(item)
    }

    override fun onSearchEditViewClear() {
        _userSearchStationName.value = ""
    }
}