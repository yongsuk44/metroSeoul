package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.usecase.FullRouteInformationUseCase
import com.young.domain.usecase.StationDataUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.model.UiConvenienceInformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

interface DetailDropBoxFunction {
    fun getConvenienceInformation(key: String, lineCode: String, trailCode: String, stationCode: String)
    fun getPlatformEntranceData(key: String, railCode: String, lineCd: String, stinCode: String)
}

class DetailDropBoxItemViewModel @ViewModelInject constructor(
    private val fullRouteInformationUseCase: FullRouteInformationUseCase
) : BaseViewModel(), DetailDropBoxFunction {

    private val _convenienceInformation = MutableLiveData<UiConvenienceInformation>()
    val convenienceInformation: LiveData<UiConvenienceInformation>
        get() = _convenienceInformation

    private val _platformEntranceOpen = MutableLiveData(false)
    val platformEntranceOpen : LiveData<Boolean>
        get() = _platformEntranceOpen

    override fun getPlatformEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ) {
        viewModelScope.launch {
            fullRouteInformationUseCase.getPlatformEntranceData(key, railCode, lineCd, stinCode)
                .flatMapConcat {
                    if (it.body.isNullOrEmpty()) throw Exception("$stinCode : 해당 코드 역에 대한 API 호출 실패")
                    else flowOf(it)
                }
                .map { it.DomainToUi() }
                .catch {
                    Timber.e(it)
                    setToastMsg("데이터를 가져오는데 실패하였습니다.")
                }
                .onStart { setLoadingValue(true) }
                .onCompletion { setLoadingValue(false) }
                .collect {
                    it
                }
        }
    }

    suspend fun getPlatformStartMovePathData() {

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