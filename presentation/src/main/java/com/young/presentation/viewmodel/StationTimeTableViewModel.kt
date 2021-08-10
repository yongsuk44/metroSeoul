package com.young.presentation.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.young.domain.usecase.info.detail.timetable.RemoteTimeTableUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi

import com.young.presentation.model.UiTrailTimeTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

interface StationTimeTableFunction {
    fun getStationTimeTable(railCode: String, lineCode: String, stationCode: String)
}

class StationTimeTableViewModel @ViewModelInject constructor(
    private val provider: ResourceProvider,
    private val timeTableUseCase: RemoteTimeTableUseCase
) : BaseViewModel(), StationTimeTableFunction {

    private val _timeTableData = MutableLiveData<List<UiTrailTimeTable>>()
    val timeTableData: LiveData<List<UiTrailTimeTable>>
        get() = _timeTableData

    override fun getStationTimeTable(railCode: String, lineCode: String, stationCode: String) {
        viewModelScope.launch(handler) {
            val weekFlow = timeTableUseCase.getTrailTimetables(
                provider.getString(R.string.trailKey),
                railCode,
                "8",
                lineCode,
                stationCode
            )
            val satFlow = timeTableUseCase.getTrailTimetables(
                provider.getString(R.string.trailKey),
                railCode,
                "7",
                lineCode,
                stationCode
            )
            val sunFlow = timeTableUseCase.getTrailTimetables(
                provider.getString(R.string.trailKey),
                railCode,
                "9",
                lineCode,
                stationCode
            )

            combine(weekFlow, satFlow, sunFlow) { week, sat, sun ->

                listOf(
                    week.DomainToUi() ,
                    if (sat.body.isNullOrEmpty()) sun.DomainToUi() else sat.DomainToUi() ,
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
                    _timeTableData.value = it
                }
        }
    }
}