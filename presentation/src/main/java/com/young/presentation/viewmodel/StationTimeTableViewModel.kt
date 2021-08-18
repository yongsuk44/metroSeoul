package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.usecase.local.LocalAllStationCodeUseCase
import com.young.domain.usecase.remote.RemoteTimeTableBaseUseCase
import com.young.domain.usecase.remote.RemoteTimeTableUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.model.IndexAllRouteInformation

import com.young.presentation.model.UiTrailTimeTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalTime

interface StationTimeTableFunction {
    fun getStationTimeTable(indexAllRouteInformation: IndexAllRouteInformation , dayCode : String)
    fun changeDayCode(data : String)
}

class StationTimeTableViewModel @ViewModelInject constructor(
    private val provider: ResourceProvider,
    private val timeTableUseCase: RemoteTimeTableUseCase ,
    private val localAllStationCodeUseCase: LocalAllStationCodeUseCase
) : BaseViewModel(), StationTimeTableFunction {

    private val _dayCodeChangeData = MutableLiveData<String>()
    val dayCodeChangeData : LiveData<String>
        get() = _dayCodeChangeData

    private val _weekTimeTable = MutableLiveData<UiTrailTimeTable>()
    val weekTimeTable: LiveData<UiTrailTimeTable>
        get() = _weekTimeTable

    private val _satTimeTable = MutableLiveData<UiTrailTimeTable>()
    val satTimeTable: LiveData<UiTrailTimeTable>
        get() = _satTimeTable

    private val _sunTimeTable = MutableLiveData<UiTrailTimeTable>()
    val sunTimeTable: LiveData<UiTrailTimeTable>
        get() = _sunTimeTable

    fun call(indexAllRouteInformation: IndexAllRouteInformation) {
        viewModelScope.launch {
            localAllStationCodeUseCase.findStationCode("S22")
                .map {
                    if (it == null) getStationTimeTable(indexAllRouteInformation , dayCodeChangeData.value ?: "1")
                    else
                }
        }
    }

    override fun getStationTimeTable(indexAllRouteInformation: IndexAllRouteInformation , dayCode : String) {
        viewModelScope.launch(handler) {
            val upFlow = generateStationTimeTableFlow(dayCode , indexAllRouteInformation, "1")
            val downFlow = generateStationTimeTableFlow(dayCode , indexAllRouteInformation , "2")

            combineTransform(upFlow,downFlow) { up , down ->

                emit(
                    UiTrailTimeTable(
                        up.body ,
                        down.body,
                        up.firstTime ,
                        up.lastTime,
                        down.firstTime,
                        down.lastTime
                    )
                )
            }
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e(e)
                }
                .onCompletion {
                    setLoadingValue(false)
                }
                .collect {

                }
        }
    }

    suspend fun generateStationTimeTableFlow(dayCode : String , data : IndexAllRouteInformation , upDown : String) =
        timeTableUseCase.getStationTimetables(provider.getString(R.string.trailKey), data.railOprIsttCd, dayCode, data.lnCd, data.stinCd, upDown)

    override fun changeDayCode(data: String) {
        _dayCodeChangeData.value = data
    }
}