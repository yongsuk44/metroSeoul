package com.young.domain.model

data class DomainStationTimeTable(
    val body: List<String>,
    val firstTime : String ,
    val lastTime : String
)

data class Header(
    val resultCnt: Int,
    val resultCode: String,
    val resultMsg: String
)

/**
 * @param dayCd : 7 토요일 8 평일 9 휴일
 * @param lnCd 선 코드
 * @param railOprIsttCd 철도 운영기관 코드
 * @param stinCd 역 코드
 * @param dptTm 출발 시간
 * @param arvTm 도착 시간
 * @param dayNm 요일 명
 */
data class TimeTableBody(
    val arvTm: String,
    val lnCd: String,
    val railOprIsttCd: String,
    val stinCd: String
)