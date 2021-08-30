package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.model.DomainTrailCodeAndLineCode
import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.FullRouteInformationUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.Event
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.model.ListRouteInformation
import com.young.presentation.modelfunction.FullRouteInformationCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

@FlowPreview
class FullRouteInformationViewModel @ViewModelInject constructor(
    private val fullRouteInformationUseCase: FullRouteInformationUseCase,
    private val allStationCodeUseCase: AllStationCodeUseCase,
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
            fullRouteInformationUseCase.getDataSize()
                .flowOn(Dispatchers.IO)
                .take(1)
                .collect { size ->
                    if (size > 0)
                        getCacheFullRouteInformation()
                    else
                        getFullRouteInformation(resourceProvider.getString(R.string.trailKey))
                }
        }
    }

    override suspend fun getFullRouteInformation(key: String) {
        fullRouteInformationUseCase.getStationRouteInformation(key)
            .flowOn(Dispatchers.IO)
            .flatMapConcat {

                val lineCodeAndTrailCodeList = it.map {
                    DomainTrailCodeAndLineCode(it.railOprIsttCd, it.lnCd)
                }.distinctBy {
                    it.lnCd to it.railOprIsttCd
                }

                fullRouteInformationUseCase.insertLineCodeAndTrailCode(lineCodeAndTrailCodeList)
                fullRouteInformationUseCase.insert(it)
            }
            .flowOn(Dispatchers.IO)
            .catch { e ->
                Timber.e(e)
                _failedInformationData.value = true
            }.collect {
                getCacheFullRouteInformation()
            }
    }

    override suspend fun getCacheFullRouteInformation() {
        fullRouteInformationUseCase.getAllData()
            .flowOn(Dispatchers.IO)
            .map {
                it.DomainToUi()
            }
            .flowOn(Dispatchers.Default)
            .catch { e ->
                Timber.e(e)
                _failedInformationData.value = true
            }.collect {
                _fullRouteInformation.value = it
            }
    }

    override fun insertAllStationCodes() {
        viewModelScope.launch {
            val code = fullRouteInformationUseCase.getAllStationCode(resourceProvider.getString(R.string.seoulKey)).single()
            allStationCodeUseCase.insert(code).collect()
        }
    }

    override fun onSearchEditViewClick(value: Boolean) {
        _searchEditViewClick.value = Event(value)
    }

    override fun onStationClick(item: ListRouteInformation, position: Int) {
        _selectPosition.value = position
        _searchActionStation.value = Event(item)
    }

    override fun onSearchEditViewClear() {
        _userSearchStationName.value = ""
    }
}