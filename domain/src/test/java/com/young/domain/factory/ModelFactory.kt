package com.young.domain.factory

import com.young.domain.factory.DataFactory.getRandomInt
import com.young.domain.factory.DataFactory.getRandomString
import com.young.domain.model.*

object ModelFactory {

    fun generateRowData() : DomainRow = DomainRow("822" , "8" , "2811" , "복정역")
    fun generatePublicDomainStationTimeTable() : DomainStationTimeTable = DomainStationTimeTable(
        listOf("test" , "test1" , "test2") ,
        "03:00:00" ,
        "23:00:46"
    )

    fun generateSeoulDomainStationTimeTable() : DomainStationTimeTable = DomainStationTimeTable(
        listOf("seoul1" , "seoul2" , "seoul3") ,
        "03:00:00" ,
        "23:00:46"
    )

    fun generateDataHeader() = Header(
        getRandomInt(),
        getRandomString(),
        getRandomString()
    )
    fun generatePlatformEntranceData() = DomainPlatformEntrance(
        header = generateDataHeader() ,
        body = listOf(generatePlatformEntranceBodyData() , generatePlatformEntranceBodyData())
    )

    fun generateNullPlatformEntranceData() = DomainPlatformEntrance(
        header = generateDataHeader() ,
        body = null
    )

    fun generatePlatformEntranceBodyData() = DomainPlatformEntranceBody(
        edMovePath = getRandomString(),
        elvtSttCd = null,
        elvtTpCd = null,
        exitMvTpOrdr = getRandomInt() ,
        imgPath = getRandomString() ,
        mvContDtl = getRandomString(),
        mvPathMgNo = getRandomInt(),
        stMovePath = getRandomString()
    )
}