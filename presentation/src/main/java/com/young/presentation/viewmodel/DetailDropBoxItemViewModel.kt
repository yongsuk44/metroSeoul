package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.usecase.remote.PlatformEntranceUseCase
import com.young.domain.usecase.remote.RemoteFullRouteInformationUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.model.UiConvenienceInformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

interface DetailDropBoxFunction {
    fun getConvenienceInformation(lineCode: String, trailCode: String, stationCode: String)

    fun getPlatformEntranceData(railCode: String, lineCd : String, stinCode: String)
}

class DetailDropBoxItemViewModel @ViewModelInject constructor(
    private val provider: ResourceProvider,
    private val allInformationUseCase: RemoteFullRouteInformationUseCase,
    private val platformEntranceUseCase: PlatformEntranceUseCase
) : BaseViewModel() ,DetailDropBoxFunction {

    private val _convenienceInformation = MutableLiveData<UiConvenienceInformation>()
    val convenienceInformation: LiveData<UiConvenienceInformation>
        get() = _convenienceInformation

    override fun getPlatformEntranceData(railCode: String,lineCd : String, stinCode: String){
        viewModelScope.launch(handler) {
            platformEntranceUseCase.getPlatformEntranceData(provider.getString(R.string.trailKey), railCode, lineCd , stinCode)
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