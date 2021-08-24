package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.young.domain.model.Row
import com.young.domain.usecase.cache.CacheAllStationCodeUseCase
import com.young.domain.usecase.remote.RemoteTimeTableBaseUseCase
import com.young.domain.usecase.remote.RemoteTimeTableUseCase
import com.young.presentation.R
import com.young.presentation.consts.BaseViewModel
import com.young.presentation.consts.CustomTransformationDataMap
import com.young.presentation.consts.DayType
import com.young.presentation.consts.ResourceProvider
import com.young.presentation.mapper.DomainToUiMapper.DomainToUi
import com.young.presentation.model.IndexAllRouteInformation
import com.young.presentation.model.UiTrailTimeTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalTime
import kotlin.math.abs

interface StationTimeTableFunction {
    fun getStationTimeTable(indexAllRouteInformation: IndexAllRouteInformation? , day: DayType)
    fun changeDayCode(data : DayType)
}

class StationTimeTableViewModel @ViewModelInject constructor(
    private val provider: ResourceProvider,
    private val timeTableUseCase: RemoteTimeTableUseCase ,
    private val localAllStationCodeUseCase: CacheAllStationCodeUseCase
) : BaseViewModel(), StationTimeTableFunction {

    private val _dayCodeChangeData = MutableLiveData<DayType>()
    val dayCodeChangeData : LiveData<DayType>
        get() = _dayCodeChangeData

    private val _timeTable = MutableLiveData<UiTrailTimeTable>()
    val timeTable: LiveData<UiTrailTimeTable>
        get() = _timeTable

    val timeTableUpPosition : LiveData<Int> = CustomTransformationDataMap(_timeTable) {
        it.up.nowTimeNearList()
    }

    val timeTableDownPosition : LiveData<Int> = CustomTransformationDataMap(_timeTable) {
        it.down.nowTimeNearList()
    }

    override fun getStationTimeTable(indexAllRouteInformation: IndexAllRouteInformation? , day: DayType) {
        viewModelScope.launch(handler) {

            if (indexAllRouteInformation == null) return@launch

            localAllStationCodeUseCase.findStationCode(indexAllRouteInformation.stinCd)
                .flatMapConcat { row ->
                    if (row != null) {
                        getSeoulStationTimeTableAPI(getDayCode(day , row) , row.STATION_CD)
                    } else {
                        getPublicStationTimeTableAPI(getDayCode(day , row), indexAllRouteInformation)
                    }
                }
                .map {
                    if (it.up.isEmpty()) getPublicStationTimeTableAPI(day.public , indexAllRouteInformation).single()
                    else it
                }
                .flowOn(Dispatchers.IO)
                .onStart {
                    setLoadingValue(true)
                }.onCompletion {
                    setLoadingValue(false)
                }.collect {
                    _timeTable.value = it
                }
        }
    }

    suspend fun generateStationTimeTableFlow(dayCode : String , data : IndexAllRouteInformation , upDown : String) =
        timeTableUseCase.getStationTimetables(provider.getString(R.string.trailKey), data.railOprIsttCd, dayCode, data.lnCd, data.stinCd, upDown)

    suspend fun generateSeoulStationTimeTableFlow(dayCode: String , code : String , upDown: String) =
        timeTableUseCase.getSeoulStationTimeTable(provider.getString(R.string.seoulKey) , upDown , dayCode , code)

    suspend fun getPublicStationTimeTableAPI(dayCode: String , indexAllRouteInformation: IndexAllRouteInformation) : Flow<UiTrailTimeTable> {
        val upFlow = generateStationTimeTableFlow(dayCode , indexAllRouteInformation, "1")
        val downFlow = generateStationTimeTableFlow(dayCode , indexAllRouteInformation , "2")

        return combineTransform(upFlow,downFlow) { up , down ->
            emit(UiTrailTimeTable(up.body , down.body, up.firstTime , up.lastTime, down.firstTime, down.lastTime))
        }
    }

    suspend fun getSeoulStationTimeTableAPI(dayCode: String , stationCode : String) : Flow<UiTrailTimeTable> {
        val upFlow = generateSeoulStationTimeTableFlow(dayCode , stationCode , "1" )
        val downFlow = generateSeoulStationTimeTableFlow(dayCode , stationCode , "2" )

        return combineTransform(upFlow,downFlow) { up , down ->
            emit(UiTrailTimeTable(up.body , down.body, up.firstTime , up.lastTime, down.firstTime, down.lastTime))
        }
    }

    override fun changeDayCode(data: DayType) {
        _dayCodeChangeData.value = data
    }

    fun getDayCode(day : DayType , data : Row?) : String = when(day) {
        DayType.WEEK -> if (data == null) day.public else day.seoul
        DayType.SAT -> if (data == null) day.public else day.seoul
        DayType.SUN -> if (data == null) day.public else day.seoul
    }


    fun List<String>.nowTimeNearList() : Int {
        val min = 1000
        val target = LocalTime.now().toSecondOfDay()

        find { s ->
            abs(min) > abs(LocalTime.parse(s).toSecondOfDay() - target)
        }.run {
            return this@nowTimeNearList.indexOf(this)
        }
    }
}