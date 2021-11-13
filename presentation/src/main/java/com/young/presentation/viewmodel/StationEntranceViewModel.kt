package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.usecase.FullRouteInformationUseCase
import com.young.presentation.consts.BaseResult
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.Event
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.model.StationEntranceBody
import com.young.presentation.model.UiConvenienceInformation
import com.young.presentation.model.UiStationEntrance
import com.young.presentation.modelfunction.StationEntranceFunction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

class StationEntranceViewModel @ViewModelInject constructor(
    private val fullRouteInformationUseCase: FullRouteInformationUseCase
) : BaseViewModel(), StationEntranceFunction {

    private val _convenienceInformation = MutableLiveData<UiConvenienceInformation>()
    val convenienceInformation: LiveData<UiConvenienceInformation>
        get() = _convenienceInformation

    private val _stationEntranceOpen = MutableLiveData(false)
    val stationEntranceOpen: LiveData<Boolean>
        get() = _stationEntranceOpen

    private val _stationEntranceData = MutableLiveData<BaseResult<UiStationEntrance>>()
    val stationEntranceData: LiveData<BaseResult<UiStationEntrance>>
        get() = _stationEntranceData

    private val _stationEntranceNumberList = MutableLiveData<List<Pair<String, List<StationEntranceBody>>>>()
    val stationEntranceNumberList: LiveData<List<Pair<String, List<StationEntranceBody>>>>
        get() = _stationEntranceNumberList

    private val _entranceGuideData = MutableLiveData<Pair<String , List<String>>>()
    val entranceGuideData: LiveData<Pair<String , List<String>>>
        get() = _entranceGuideData

    private val _photoData = MutableLiveData<Event<String>>()
    val photoData : LiveData<Event<String>>
        get() = _photoData

    override fun onEntranceGuideOpen() {
        _stationEntranceOpen.value = !stationEntranceOpen.value!!
    }

    override fun getStationEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ) {
        viewModelScope.launch {
            fullRouteInformationUseCase.getStationEntranceData(key, railCode, lineCd, stinCode)
                .flatMapConcat {
                    if (it.body.isNullOrEmpty()) throw NullPointerException("$stinCode : 해당 코드 역에 대한 API 호출 실패")
                    else flowOf(it)
                }
                .map { it.DomainToUi() }
                .catch { _stationEntranceData.value = BaseResult.Failed(it) }
                .onStart { _stationEntranceData.value = BaseResult.Loading(true) }
                .collect { _stationEntranceData.value = BaseResult.Success(it) }
        }
    }

    override fun onEntranceNumberList(item: List<Pair<String, List<StationEntranceBody>>>) {
        _stationEntranceNumberList.value = item
    }

    override fun onEntranceGuideView(item: Pair<String , List<StationEntranceBody>>?) {
        item?.let {
            _entranceGuideData.value = item.second.first().image to item.second.map { it.description }
        } ?: setToastMsg("해당 역이 없습니다.")
    }

    override fun onGuidePhotoMove(photoUrl: String) {
        _photoData.value = Event(photoUrl)
    }

    override fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    ) {
        viewModelScope.launch(handler) {
            fullRouteInformationUseCase.getConvenienceInformation(
                key,
                lineCode,
                trailCode,
                stationCode
            )
                .map { it.DomainToUi() }
                .flowOn(Dispatchers.IO)
                .catch { Timber.e(it) }
                .collect { _convenienceInformation.value = it }
        }
    }

}