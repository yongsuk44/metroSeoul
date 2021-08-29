package com.young.data.model

import androidx.room.*


/**
 *
 *  @param lnCd    선코드
 *  @param mreaWideCd    권역코드
 *  @param railOprIsttCd    철도운영기관코드
 *  @param routCd    노선코드
 *  @param routNm    노선명
 *  @param stinCd    역코드
 *  @param stinConsOrdr    역구성순서
 *  @param stinNm    역명
 */

@Entity
data class CacheFullRouteInformation(
    @PrimaryKey
    val stinCd: String,
    val mreaWideCd: String?,
    val routCd: String,
    val routNm: String?,
    @Embedded val cacheTrailCodeAndLineCode: CacheTrailCodeAndLineCode,
    val stinConsOrdr: String,
    val stinNm: String
)
