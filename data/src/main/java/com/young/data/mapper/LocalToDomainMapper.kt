package com.young.data.mapper

import com.young.data.model.FullRouteInformation
import com.young.data.model.LocalTrailCodeAndLineCode
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.AllRouteInformation
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.valueParameters

object LocalToDomainMapper {


    fun AllRouteInformation.DomainToLocal() = with(::FullRouteInformation) {
        FullRouteInformation(
            stinCd = stinCd,
            mreaWideCd = mreaWideCd,
            localTrailCodeAndLineCode = LocalTrailCodeAndLineCode(railOprIsttCd, lnCd),
            routCd = routCd,
            routNm = routNm,
            stinConsOrdr = stinConsOrdr,
            stinNm = stinNm
        )
    }

    fun FullRouteInformation.LocalToDomain() = with(::AllRouteInformation) {
        AllRouteInformation(
            stinCd = stinCd,
            mreaWideCd = mreaWideCd,
            routCd = routCd,
            routNm = routNm,
            stinConsOrdr = stinConsOrdr,
            stinNm = stinNm,
            lnCd = localTrailCodeAndLineCode.lnCd ,
            railOprIsttCd = localTrailCodeAndLineCode.railOprIsttCd
        )
    }
}