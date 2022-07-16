package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.base.di.IoDispatcher
import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.model.DomainTrailCodeAndLineCode
import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.FullRouteInformationUseCase
import com.young.domain.usecase.information.FindStationRouteInformationUseCase
import com.young.domain.usecase.information.InsertStationCodeUseCase
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.CustomTransformationDataMap
import com.young.presentation.consts.Event
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.model.ListRouteInformation
import com.young.presentation.modelfunction.FullRouteInformationCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*

@ExperimentalCoroutinesApi
@FlowPreview
class FullRouteInformationViewModel @ViewModelInject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val findStationRouteInformationUseCase: FindStationRouteInformationUseCase,
    private val insertStationCodeUseCase: InsertStationCodeUseCase
) : BaseViewModel(), FullRouteInformationCase {

    private val _failedInformationData = MutableLiveData(false)
    val failedInformationData: LiveData<Boolean>
        get() = _failedInformationData

    private val _popupWindowVisible = MutableLiveData(false)
    val popupWindowVisible: LiveData<Boolean>
        get() = _popupWindowVisible

    private val _fullRouteInformation = MutableLiveData<List<ListRouteInformation>>()
    val fullRouteInformation: LiveData<List<ListRouteInformation>>
        get() = _fullRouteInformation

    val _userSearchStationName = MutableLiveData<String>()
    val userSearchStationName: LiveData<String>
        get() = _userSearchStationName

    val filterList = CustomTransformationDataMap(userSearchStationName) {
        fullRouteInformation.value?.filter { list ->
            list.stinNm.toLowerCase(Locale.KOREAN).contains(it.toLowerCase(Locale.KOREAN))
        }
    }

    private val _searchActionStation = MutableLiveData<Event<ListRouteInformation>>()
    val searchActionStation: LiveData<Event<ListRouteInformation>>
        get() = _searchActionStation

    private val _searchEditViewClick = MutableLiveData<Event<Boolean>>()
    val searchEditViewClick: LiveData<Event<Boolean>>
        get() = _searchEditViewClick


    override fun loadFullRouteInformation(trailKey: String) {
        viewModelScope.launch(ioDispatcher) {
            findStationRouteInformationUseCase.onFindStationRouteInformation(trailKey)
                .catch {
                    Timber.e(it)
                    _failedInformationData.value = true
                }
                .map { it.DomainToUi() }
                .collect { list ->
                    _fullRouteInformation.value = list
                }
        }
    }

    override fun insertAllStationCodes(seoulKey: String) {
        viewModelScope.launch(ioDispatcher) {
            insertStationCodeUseCase.onInsertStationCode(seoulKey)
                .catch { Timber.e(it) }
                .collect { }
        }
    }

    override fun onSearchEditViewClick(value: Boolean) {
        _searchEditViewClick.value = Event(value)
    }

    override fun onStationClick(item: ListRouteInformation) {
        _searchActionStation.value = Event(item)
        Timber.d("검색한 역 : $item")
    }

    override fun onSearchEditViewClear() {
        _userSearchStationName.value = ""
    }

    override fun onPopupWindowViewVisibleCheck(check: Boolean) {
        _popupWindowVisible.value = check
    }
}