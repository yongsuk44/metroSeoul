package com.young.presentation.model

data class UiAllRouteInformation(
    val body: List<ListRouteInformation>,
    val header: Header
)

/**
 *
 *  @param lnCd	선코드
 *  @param mreaWideCd	권역코드
 *  @param railOprIsttCd	철도운영기관코드
 *  @param routCd	노선코드
 *  @param routNm	노선명
 *  @param stinCd	역코드
 *  @param stinConsOrdr	역구성순서
 *  @param stinNm	역명
 */

data class ListRouteInformation(
    val mreaWideCd: String?,
    val railOprIsttCd: List<String>,
    val routCd: String,
    val routNm: String?,
    val lnCd: List<String>,
    val stinCd: List<String>,
    val stinConsOrdr: String,
    val stinNm: String
)

data class IndexAllRouteInformation(
    val mreaWideCd: String?,
    val railOprIsttCd: String,
    val routCd: String,
    val routNm: String?,
    val lnCd: String,
    val stinCd: String,
    val stinConsOrdr: String,
    val stinNm: String
)
