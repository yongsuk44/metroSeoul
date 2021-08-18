package com.young.data_remote.mapper

import com.young.data_remote.model.RemoteStationSeoulTimeTable
import com.young.data_remote.model.RemoteStationTimeTable
import com.young.domain.model.DomainStationTimeTable
import com.young.domain.model.TimeTableBody
import kotlinx.coroutines.flow.*

object timeTableMapper {

    fun getLocalTime(time: String): String = StringBuilder().append(time)
        .insert(time.length-4, ":")
        .insert(time.length-1, ":")
        .toString()

    suspend fun RemoteStationTimeTable.RemoteToDomain(upDown : String) : DomainStationTimeTable? {
        if (body.isNullOrEmpty()) return null
        else {
            val group = body!!.groupBy { it.orgStinCd < it.tmnStinCd }

            return flowOf(group.getValue(upDown == "1"))
                .transform {
                    it.map { getLocalTime(it.arvTm) }
                        .run {
                            emit(filter { it.substring(0..1).toInt() > 2 } + filter { it.substring(0..1).toInt() <= 2 })
                        }
                }.transform {
                    emit(
                        DomainStationTimeTable(it , it.first() , it.last() )
                    )
                }.first()
        }
    }

    suspend fun RemoteStationSeoulTimeTable.RemoteToDomain() : DomainStationTimeTable? {
        if (this.SearchSTNTimeTableByIDService == null) return null
        else {
            return flowOf(SearchSTNTimeTableByIDService!!.row)
                .transform {
                    val list = it.map { it.ARRIVETIME }
                    emit(
                        DomainStationTimeTable(list , list.first() , list.last())
                    )
                }.first()
        }
    }
}