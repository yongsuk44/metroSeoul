package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
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
    fun getStationTimeTable(indexAllRouteInformation: IndexAllRouteInformation)
}

class StationTimeTableViewModel @ViewModelInject constructor(
    private val provider: ResourceProvider,
    private val timeTableUseCase: RemoteTimeTableUseCase ,

) : BaseViewModel(), StationTimeTableFunction {

    private val _weekTimeTable = MutableLiveData<UiTrailTimeTable>()
    val weekTimeTable: LiveData<UiTrailTimeTable>
        get() = _weekTimeTable

    private val _satTimeTable = MutableLiveData<UiTrailTimeTable>()
    val satTimeTable: LiveData<UiTrailTimeTable>
        get() = _satTimeTable

    private val _sunTimeTable = MutableLiveData<UiTrailTimeTable>()
    val sunTimeTable: LiveData<UiTrailTimeTable>
        get() = _sunTimeTable

    override fun getStationTimeTable(indexAllRouteInformation: IndexAllRouteInformation) {
        viewModelScope.launch(handler) {
            val weekFlow = generateStationTimeTableFlow("8" , indexAllRouteInformation)
            val satFlow = generateStationTimeTableFlow("7" , indexAllRouteInformation)
            val sunFlow = generateStationTimeTableFlow("9" , indexAllRouteInformation)

            combine(weekFlow, satFlow, sunFlow) { week, sat, sun ->

                listOf(
                    week.DomainToUi() ,
                    sat.DomainToUi() ?: sun.DomainToUi() ,
                    sun.DomainToUi()
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
                    _weekTimeTable.value = it.first()
                    _satTimeTable.value = it[1]
                    _sunTimeTable.value = it.last()
                }
        }
    }

    suspend fun generateStationTimeTableFlow(dayCode : String , data : IndexAllRouteInformation) =
        timeTableUseCase.getStationTimetables(provider.getString(R.string.trailKey), data.railOprIsttCd, dayCode, data.lnCd, data.stinCd)

}