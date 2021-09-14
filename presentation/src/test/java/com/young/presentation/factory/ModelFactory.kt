package com.young.presentation.factory

import com.young.domain.model.DomainRow
import com.young.domain.model.DomainStationTimeTable
import com.young.presentation.factory.DataFactory.randomString
import com.young.presentation.model.IndexAllRouteInformation
import com.young.presentation.model.UiStationTimeTable
import kotlinx.coroutines.flow.flowOf

object ModelFactory {

    fun generateUiStationTimeTable(type : String , dayType : String = "") = UiStationTimeTable(
        listOf(type,"$type 1 $dayType","$type 2 $dayType","$type 3 $dayType"),
        listOf(type,"$type 1","$type 2","$type 3"),
        randomString(),
        randomString(),
        randomString(),
        randomString()
    )
    fun generateDomainStationTimeTable(type : String) = DomainStationTimeTable(
        listOf(type,"$type 1","$type 2","$type 3"),
        randomString(),
        randomString()
    )

    fun generateFlowListDomainStationTimeTable(size : Int , type : String) =
        List(size) {
            flowOf(generateDomainStationTimeTable("$type it"))
        }

    fun generateDomainRow() = DomainRow(
        randomString(),
        randomString(),
        randomString(),
        randomString()
    )

    fun generateDomainRow(code : String) = DomainRow(
        randomString(),
        randomString(),
        code,
        randomString()
    )

    fun generateIndexAllRouteInformation() = IndexAllRouteInformation(
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString(),
        randomString()
    )
    
    fun generateIndexAllRouteInformation(railCode :String , lnCd : String , stationCode : String) = IndexAllRouteInformation(
        randomString(),
        railCode,
        randomString(),
        randomString(),
        lnCd,
        stationCode,
        randomString(),
        randomString()
    )
}