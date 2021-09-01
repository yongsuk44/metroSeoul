package com.young.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.young.domain.model.DomainRow
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.StationDataUseCase
import com.young.presentation.R
import com.young.presentation.consts.*
import com.young.presentation.mapper.FlowMapper.domainStationTimeTableCombine
import com.young.presentation.model.IndexAllRouteInformation
import com.young.presentation.model.UiTrailTimeTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalTime
import kotlin.math.abs

interface StationTimeTableFunction {
    suspend fun findAllStationCode(stationCode: String): Flow<DomainRow?>
    fun getStationTimeTable(indexAllRouteInformation: IndexAllRouteInformation?, day: DayType)
    fun changeDayCode(data: DayType)
}

sealed class SealedTimeTableData {
    data class Success(val data: UiTrailTimeTable?) : SealedTimeTableData()
    data class Failed(val exception: Throwable) : SealedTimeTableData()
    data class Loading(val loading: Boolean) : SealedTimeTableData()
}

class StationTimeTableViewModel @ViewModelInject constructor(
    private val provider: ResourceProvider,
    private val stationDataUseCase: StationDataUseCase,
    private val allStationCodeUseCase: AllStationCodeUseCase
) : BaseViewModel(), StationTimeTableFunction {

    val seoulKey = provider.getString(R.string.seoulKey)
    val portalKey = provider.getString(R.string.trailKey)

    private val _dayCodeChangeData = MutableLiveData<DayType>()
    val dayCodeChangeData: LiveData<DayType>
        get() = _dayCodeChangeData

    private val _timeTable = MutableLiveData<SealedTimeTableData>()
    val timeTable: LiveData<SealedTimeTableData>
        get() = _timeTable

    // 서울 코드로 서울 지하철 시간표를 먼저 가져온다.
    // 시간표가 있으면 flatMapConcat 전부 패스
    // 시간표 없으면 공공 데이터 포털에서 시간표를 가져옴
    // 공공 데이터 포털에서 토요일에 대한 데이터가 없을경우 다시 일요일에 대한 데이터를 호출
    @FlowPreview
    override fun getStationTimeTable(
        indexAllRouteInformation: IndexAllRouteInformation?,
        day: DayType
    ) {
        viewModelScope.launch {
            flowOf(indexAllRouteInformation)
                .flatMapConcat {
                    if (it == null) throw NullPointerException("선택한 역에 대한 정보가 없음 : $indexAllRouteInformation")
                    else flowOf(it)
                }.flatMapConcat { indexData ->
                    findAllStationCode(indexData.stinCd)
                        .flatMapConcat {
                            getSeoulStationTimeTable(
                                getDayCode(day, it),
                                it,
                                indexData
                            )
                        }
                        .flatMapConcat { getDataNullToPublicTimeTable(it, day.public, indexData) }
                        .flatMapConcat {
                            getDataNullToPublicTimeTable(
                                it,
                                DayType.SUN.public,
                                indexData
                            )
                        }
                }.catch {
                    _timeTable.value = SealedTimeTableData.Failed(it)
                }.onStart {
                    _timeTable.value = SealedTimeTableData.Loading(true)
                }.onCompletion {
                    _timeTable.value = SealedTimeTableData.Loading(false)
                }.collect {
                    _timeTable.value = SealedTimeTableData.Success(it)
                }
        }
    }

    override suspend fun findAllStationCode(stationCode: String): Flow<DomainRow?> =
        allStationCodeUseCase.findStationCode(stationCode)

    suspend fun getSeoulStationTimeTable(
        dayCode: String,
        row: DomainRow?,
        indexAllRouteInformation: IndexAllRouteInformation
    ) = row?.let { generateSeoulStationTimeTableAPI(seoulKey,dayCode, row.STATION_CD) }
        ?: generatePublicStationTimeTableAPI(portalKey,dayCode, indexAllRouteInformation)

    suspend fun getDataNullToPublicTimeTable(
        data: UiTrailTimeTable?,
        dayCode: String,
        indexAllRouteInformation: IndexAllRouteInformation
    ): Flow<UiTrailTimeTable?> =
        data?.let {
            generatePublicStationTimeTableAPI(
                portalKey,
                dayCode,
                indexAllRouteInformation
            )
        }
            ?: flowOf(data)

    suspend fun generatePublicStationTimeTableAPI(
        key : String,
        dayCode: String,
        data: IndexAllRouteInformation
    ): Flow<UiTrailTimeTable?> =
        (1..2).map {
        stationDataUseCase.getStationTimetables(
            key,
            data.railOprIsttCd,
            dayCode,
            data.lnCd,
            data.stinCd,
            it.toString()
        )
    }.domainStationTimeTableCombine()

    suspend fun generateSeoulStationTimeTableAPI(
        key : String,
        dayCode: String,
        stationCode: String
    ): Flow<UiTrailTimeTable?> =
        (1..2).map {
        stationDataUseCase.getSeoulStationTimeTable(
            key,
            it.toString(),
            dayCode,
            stationCode
        )
    }.domainStationTimeTableCombine()

    override fun changeDayCode(data: DayType) {
        _dayCodeChangeData.value = data
    }

    fun getDayCode(day: DayType, data: DomainRow?): String = when (day) {
        DayType.WEEK -> if (data == null) day.public else day.seoul
        DayType.SAT -> if (data == null) day.public else day.seoul
        DayType.SUN -> if (data == null) day.public else day.seoul
    }
}