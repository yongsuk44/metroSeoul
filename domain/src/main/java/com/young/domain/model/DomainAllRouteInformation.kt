package com.young.domain.model

data class DomainFullRouteInformation(
    val body: List<DomainFullRouteInformationBody>,
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

data class DomainFullRouteInformationBody(
    val mreaWideCd: String?,
    val railOprIsttCd: String,
    val routCd: String,
    val routNm: String?,
    val lnCd: String,
    val stinCd: String,
    val stinConsOrdr: String,
    val stinNm: String
)
