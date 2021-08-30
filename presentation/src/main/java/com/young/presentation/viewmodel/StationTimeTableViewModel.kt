package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.young.domain.model.DomainRow
import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.StationDataUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.CustomTransformationDataMap
import com.young.presentation.consts.DayType
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.model.IndexAllRouteInformation
import com.young.presentation.model.UiTrailTimeTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalTime
import kotlin.math.abs

interface StationTimeTableFunction {
    fun getStationTimeTable(indexAllRouteInformation: IndexAllRouteInformation?, day: DayType)
    fun changeDayCode(data: DayType)
}

class StationTimeTableViewModel @ViewModelInject constructor(
    private val provider: ResourceProvider,
    private val stationDataUseCase: StationDataUseCase,
    private val allStationCodeUseCase: AllStationCodeUseCase
) : BaseViewModel(), StationTimeTableFunction {

    private val _dayCodeChangeData = MutableLiveData<DayType>()
    val dayCodeChangeData: LiveData<DayType>
        get() = _dayCodeChangeData

    private val _timeTable = MutableLiveData<UiTrailTimeTable>()
    val timeTable: LiveData<UiTrailTimeTable>
        get() = _timeTable

    val timeTableUpPosition: LiveData<Int> = CustomTransformationDataMap(_timeTable) {
        it.up.nowTimeNearList()
    }

    val timeTableDownPosition: LiveData<Int> = CustomTransformationDataMap(_timeTable) {
        it.down.nowTimeNearList()
    }

    override fun getStationTimeTable(indexAllRouteInformation: IndexAllRouteInformation?, day: DayType) {
        viewModelScope.launch(handler) {

            if (indexAllRouteInformation == null) return@launch

            allStationCodeUseCase.findStationCode(indexAllRouteInformation.stinCd)
                .flowOn(Dispatchers.IO)
                .flatMapLatest { row ->
                    row?.let { getSeoulStationTimeTableAPI(getDayCode(day, row), row.STATION_CD) }
                        ?: getPublicStationTimeTableAPI(getDayCode(day, row), indexAllRouteInformation)
                }
                .flatMapConcat {
                    if (it.up.isEmpty()) getPublicStationTimeTableAPI(day.public, indexAllRouteInformation)
                    else flowOf(it)
                }
                .flatMapConcat {
                    if (it.up.isEmpty()) getPublicStationTimeTableAPI("9" , indexAllRouteInformation)
                    else flowOf(it)
                }
                .onStart {
                    setLoadingValue(true)
                }.onCompletion {
                    setLoadingValue(false)
                }.collect {
                    _timeTable.value = it
                }
        }
    }

    suspend fun generateStationTimeTableFlow(dayCode: String, data: IndexAllRouteInformation, upDown: String) =
        stationDataUseCase.getStationTimetables(
            provider.getString(R.string.trailKey),
            data.railOprIsttCd,
            dayCode,
            data.lnCd,
            data.stinCd,
            upDown
        )

    suspend fun generateSeoulStationTimeTableFlow(dayCode: String, code: String, upDown: String) =
        stationDataUseCase.getSeoulStationTimeTable(
            provider.getString(R.string.seoulKey),
            upDown,
            dayCode,
            code
        )

    suspend fun getPublicStationTimeTableAPI(dayCode: String, indexAllRouteInformation: IndexAllRouteInformation): Flow<UiTrailTimeTable> {
        return (1..2).map {
            generateStationTimeTableFlow(dayCode, indexAllRouteInformation, it.toString())
        }.run {
            combineTransform(this) { call ->
                emit(
                    UiTrailTimeTable(
                        call.first().body,
                        call.last().body,
                        call.first().firstTime,
                        call.last().firstTime,
                        call.first().lastTime,
                        call.last().lastTime
                    )
                )
            }.flowOn(Dispatchers.IO)
        }
    }

    suspend fun getSeoulStationTimeTableAPI(dayCode: String, stationCode: String): Flow<UiTrailTimeTable> {
        return (1..2).map {
            generateSeoulStationTimeTableFlow(dayCode, stationCode, it.toString())
        }.run {
            combineTransform(this) { call ->
                emit(
                    UiTrailTimeTable(
                        call.first().body,
                        call.last().body,
                        call.first().firstTime,
                        call.last().firstTime,
                        call.first().lastTime,
                        call.last().lastTime
                    )
                )
            }.flowOn(Dispatchers.IO)
        }
    }

    override fun changeDayCode(data: DayType) {
        _dayCodeChangeData.value = data
    }

    fun getDayCode(day: DayType, data: DomainRow?): String = when (day) {
        DayType.WEEK -> if (data == null) day.public else day.seoul
        DayType.SAT -> if (data == null) day.public else day.seoul
        DayType.SUN -> if (data == null) day.public else day.seoul
    }


    fun List<String>.nowTimeNearList(): Int {
        val min = 1000
        val target = LocalTime.now().toSecondOfDay()

        find { s ->
            abs(min) > abs(LocalTime.parse(s).toSecondOfDay() - target)
        }.run {
            return this@nowTimeNearList.indexOf(this)
        }
    }
}