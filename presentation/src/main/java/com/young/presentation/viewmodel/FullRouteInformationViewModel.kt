package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.model.DomainTrailCodeAndLineCode
import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.FullRouteInformationUseCase
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.CustomTransformationDataMap
import com.young.presentation.consts.Event
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.model.ListRouteInformation
import com.young.presentation.modelfunction.FullRouteInformationCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

@ExperimentalCoroutinesApi
@FlowPreview
class FullRouteInformationViewModel @ViewModelInject constructor(
    private val fullRouteInformationUseCase: FullRouteInformationUseCase,
    private val allStationCodeUseCase: AllStationCodeUseCase
) : BaseViewModel(), FullRouteInformationCase {

    private val _failedInformationData = MutableLiveData(false)
    val failedInformationData: LiveData<Boolean>
        get() = _failedInformationData

    private val _popupWindowVisible = MutableLiveData(false)
    val popupWindowVisible : LiveData<Boolean>
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
        viewModelScope.launch {
            fullRouteInformationUseCase.readDataSize()
                .catch {
                    Timber.e(it)
                    _failedInformationData.value = true
                }
                .flatMapConcat {
                    if (it > 0) findStationRouteInformation(null)
                    else findStationRouteInformation(trailKey)
                }.collect {
                    _fullRouteInformation.value = it.DomainToUi()
                    insertFullRouteInformationDataAndTrailLineCode(it)
                }
        }
    }

    override fun insertFullRouteInformationDataAndTrailLineCode(list: List<DomainFullRouteInformationBody>) {
        viewModelScope.launch {
            flowOf(list)
                .transform {
                    fullRouteInformationUseCase.insert(it).single()
                    emit(it)
                }.mapLatest {
                    it.map { DomainTrailCodeAndLineCode(it.railOprIsttCd, it.lnCd) }
                        .distinctBy { it.lnCd to it.railOprIsttCd }
                }.collect {
                    fullRouteInformationUseCase.insertLineCodeAndTrailCode(it).single()
                }
        }
    }

    override suspend fun findStationRouteInformation(key: String?): Flow<List<DomainFullRouteInformationBody>> =
        fullRouteInformationUseCase.findStationRouteInformation(key)

    override fun insertAllStationCodes(seoulKey: String) {
        viewModelScope.launch {
            fullRouteInformationUseCase.getAllStationCode(seoulKey)
                .catch { Timber.e(it) }
                .collect { allStationCodeUseCase.insert(it).single() }
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