package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.usecase.info.basic.remote.RemoteFullRouteInformationUseCase
import com.young.domain.usecase.info.detail.platformentrance.PlatformEntranceUseCase
import com.young.domain.usecase.info.detail.timetable.RemoteTimeTableUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.mapper.DomainToUiMapper.DomaionToUi
import com.young.presentation.model.UiConvenienceInformation
import com.young.presentation.model.UiTrailTimeTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

interface DetailDropBoxFunction {
    fun getConvenienceInformation(lineCode: String, trailCode: String, stationCode: String)
    fun getTrailTimetables(railCode: String, dayCd: String, lineCode: String, stationCode: String)
    fun getPlatformAtTheEntranceData(railCode: String, lineCd : String, stinCode: String)
}

class DetailDropBoxItemViewModel @ViewModelInject constructor(
    private val provider: ResourceProvider,
    private val timeTableUseCase: RemoteTimeTableUseCase,
    private val allInformationUseCase: RemoteFullRouteInformationUseCase,
    private val platformEntranceUseCase: PlatformEntranceUseCase
) : BaseViewModel() ,DetailDropBoxFunction {

    private val _convenienceInformation = MutableLiveData<UiConvenienceInformation>()
    val convenienceInformation: LiveData<UiConvenienceInformation>
        get() = _convenienceInformation

    private val _timeTableData = MutableLiveData<UiTrailTimeTable>()
    val timeTableData: LiveData<UiTrailTimeTable>
        get() = _timeTableData

    override fun getPlatformAtTheEntranceData(railCode: String,lineCd : String, stinCode: String){
        viewModelScope.launch(handler) {
            platformEntranceUseCase.getPlatformAtTheEntranceData(provider.getString(R.string.trailKey), railCode, lineCd , stinCode)
                .map {
                    it.DomainToUi()
                }
                .flatMapConcat {
                    if (it.header.resultCode != "00") throw Exception("$stinCode : 해당 코드 역에 대한 API 호출 실패")
                    else flowOf(it.body)
                }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e(e)
                }
                .onCompletion {
                    setLoadingValue(false)
                }
                .collect {
                    it
                }
        }
    }

    override fun getTrailTimetables(railCode: String, dayCd: String, lineCode: String, stationCode: String) {
        viewModelScope.launch(handler) {
            timeTableUseCase.getTrailTimetables(provider.getString(R.string.trailKey), railCode, dayCd, lineCode, stationCode)
                .map {
                    it.DomaionToUi()
                }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e(e)
                }
                .onCompletion {
                    setLoadingValue(false)
                }
                .collect {
                    _timeTableData.value = it
                }
        }
    }

    override fun getConvenienceInformation(lineCode: String, trailCode: String, stationCode: String) {
        viewModelScope.launch(handler) {
            allInformationUseCase.getConvenienceInformation(provider.getString(R.string.trailKey), lineCode, trailCode, stationCode)
                .map {
                    it.DomainToUi()
                }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e(e)
                }.onCompletion {
                    setLoadingValue(false)
                }.collect {
                    _convenienceInformation.value = it
                }
        }
    }

}