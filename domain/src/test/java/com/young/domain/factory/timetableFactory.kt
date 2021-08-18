package com.young.domain.factory

import com.young.domain.model.DomainStationTimeTable
import com.young.domain.model.Row

object timetableFactory {

    fun generateRowData() : Row = Row("822" , "8" , "2811" , "복정역")
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
}