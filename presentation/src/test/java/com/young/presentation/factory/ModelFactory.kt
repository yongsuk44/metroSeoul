package com.young.presentation.factory

import com.young.domain.model.DomainRow
import com.young.domain.model.DomainStationTimeTable
import com.young.presentation.factory.DataFactory.randomString
import com.young.presentation.model.UiTrailTimeTable

object ModelFactory {

    fun generateUiTrailTimeTable() = UiTrailTimeTable(
        listOf(randomString(),randomString(),randomString()),
        listOf(randomString(),randomString(),randomString(),randomString()),
        randomString(),
        randomString(),
        randomString(),
        randomString()
    )
    fun generateDomainTrailTimeTable() = DomainStationTimeTable(
        listOf(randomString(),randomString(),randomString(),randomString()),
        randomString(),
        randomString()
    )

    fun generateDomainRow() = DomainRow(
        randomString(),
        randomString(),
        randomString(),
        randomString()
    )

}