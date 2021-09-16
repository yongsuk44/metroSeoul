package com.young.data

import com.young.data.DataFactory.getRandomInt
import com.young.data.DataFactory.getRandomString
import com.young.data.model.DataHeader
import com.young.data.model.DataPlatformEntrance
import com.young.data.model.DataPlatformEntranceBody

object ModelFactory {
    fun generateDataHeader() = DataHeader(
        getRandomInt(),
        getRandomString(),
        getRandomString()
    )
    fun generatePlatformEntranceData() = DataPlatformEntrance(
        header = generateDataHeader() ,
        body = listOf(generatePlatformEntranceBodyData() , generatePlatformEntranceBodyData())
    )

    fun generateNullPlatformEntranceData() = DataPlatformEntrance(
        header = generateDataHeader() ,
        body = null
    )

    fun generatePlatformEntranceBodyData() = DataPlatformEntranceBody(
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