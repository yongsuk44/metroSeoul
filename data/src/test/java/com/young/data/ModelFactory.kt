package com.young.data

import com.young.data.DataFactory.getRandomInt
import com.young.data.DataFactory.getRandomString
import com.young.data.model.DataHeader
import com.young.data.model.DataStationEntrance
import com.young.data.model.DataStationEntranceBody

object ModelFactory {
    fun generateDataHeader() = DataHeader(
        getRandomInt(),
        getRandomString(),
        getRandomString()
    )
    fun generateStationEntranceData() = DataStationEntrance(
        header = generateDataHeader() ,
        body = listOf(generateStationEntranceBodyData() , generateStationEntranceBodyData())
    )

    fun generateNullStationEntranceData() = DataStationEntrance(
        header = generateDataHeader() ,
        body = null
    )

    fun generateStationEntranceBodyData() = DataStationEntranceBody(
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