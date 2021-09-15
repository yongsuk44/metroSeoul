package com.young.presentation.modelfunction

import com.young.domain.model.DomainRow
import com.young.presentation.consts.DayType
import com.young.presentation.model.IndexAllRouteInformation
import com.young.presentation.model.UiStationTimeTable
import kotlinx.coroutines.flow.Flow

interface StationTimeTableFunction {
    /**
     * 공공 데이터 포털 및 서울 공공 데이터에서 사용되는 고유 P-key 가져옴
     * @param stationCode 해당 코드를 통해서 Room에서 조회
     */
    suspend fun findAllStationCode(stationCode: String): Flow<DomainRow?>

    /**
     * 지하철 역 시간표 조회 로직 실행
     */
    fun getStationTimeTable(
        indexAllRouteInformation: IndexAllRouteInformation?,
        day: DayType,
        seoulKey: String,
        portalKey: String
    )

    /**
     * 평일 , 토요일 , 일요일 날짜를 변경하여 시간표 조회 재실행
     */
    fun changeDayCode(data: DayType)

    /**
     * Room에서 서울 공공데이터에서 사용되는 고유 P-key Code 조회 후
     * 1. Code가 있을경우 서울 공공 데이터에서 시간표 조회
     * 2. Code가 없을경우 공공 데이터 포털에서 시간표 조회
     */
    suspend fun findCodeTrueSeoulTimeTableFalsePortalTimeTable(
        key: String,
        data: DomainRow?,
        dayCode: String,
        indexData: IndexAllRouteInformation
    ): Flow<UiStationTimeTable?>

    /**
     * 다음 상황일 때 사용
     * 1. 시간표 조회 중 서울 공공 데이터에서 조회를 하지 못하였을 경우 공공 데이터 포털에서 다시 조회 요청
     * 2. 공공 데이터 포털에서 간헐적으로 토요일에 대한 시간표를 제공하지 않아서,
     * 토요일에 대한 데이터가 없을경우 일요일 및 공휴일에 대한 시간표를 조회 요청
     */
    suspend fun emptyTimeTableToPortalTimeTableCall(
        key: String,
        data: UiStationTimeTable?,
        dayCode: String,
        indexData: IndexAllRouteInformation
    ): Flow<UiStationTimeTable?>

    /**
     * 공공 데이터 포털에서 상행 및 하행에 대한 시간표를 합쳐주는 함수
     */
    suspend fun combinePublicStationTimeTableAPI(
        key: String,
        dayCode: String,
        data: IndexAllRouteInformation
    ): Flow<UiStationTimeTable?>

    /**
     * 서울 공공 데이터에서 상행 및 하행에 대한 시간표를 합쳐주는 함수
     */
    suspend fun combineSeoulStationTimeTableAPI(
        key: String,
        dayCode: String,
        stationCode: String
    ): Flow<UiStationTimeTable?>
}