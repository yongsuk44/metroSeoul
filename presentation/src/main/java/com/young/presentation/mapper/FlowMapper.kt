package com.young.presentation.mapper

import com.young.domain.model.DomainStationTimeTable
import com.young.presentation.model.UiStationTimeTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combineTransform

object FlowMapper {

    fun List<Flow<DomainStationTimeTable?>>.domainStationTimeTableCombine() =
        combineTransform(this) { table ->
            emit(
                if (table.isNullOrEmpty()) null
                else {
                    val up = table.first()!!
                    val down = table.last()!!
                    UiStationTimeTable(
                        up.body,
                        down.body,
                        up.firstTime,
                        down.firstTime,
                        up.lastTime,
                        down.lastTime
                    )
                }
            )
        }

}