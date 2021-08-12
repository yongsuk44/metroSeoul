package com.young.data_remote.mapper

import com.young.data_remote.model.RemoteStationTimeTable
import com.young.domain.model.TestTimeTable
import com.young.domain.model.TimeTableBody
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.transform

object timeTableMapper {

    fun getLocalTime(time: String): String = StringBuilder().append(time)
        .insert(time.length-4, ":")
        .insert(time.length-1, ":")
        .toString()

    suspend fun RemoteStationTimeTable.RemoteToDomain() : TestTimeTable? {
        if (body.isNullOrEmpty()) return null
        else {
            val group = body!!.groupBy { it.orgStinCd < it.tmnStinCd }
            val upTable = group.getValue(true).groupBy { it.orgStinCd }
            val downTable = group.getValue(false).groupBy { it.orgStinCd }

            val bodyObject = body!!.map {
                TimeTableBody(
                    arvTm = it.arvTm,
                    lnCd = it.lnCd,
                    railOprIsttCd = it.railOprIsttCd,
                    stinCd = it.stinCd ,
                    tmnStinCd = it.tmnStinCd ,
                    orgStinCd = it.orgStinCd,
                    trnNo = it.trnNo ,
                    dptTm =it.dptTm ,
                    dayCd =it.dayCd ,
                    dayNm =it.dayNm
                )
            }

            return flowOf(bodyObject)
                .transform {
                    it.map {
                        getLocalTime(it.arvTm)
                    }.run {
                        emit(filter { it.substring(0..1).toInt() > 1 })
                        emit(filter { it.substring(0..1).toInt() <= 1 })
                    }
                }
                .map {
                    val min = it.first().toString()
                    val max = it.last().toString()
                    TestTimeTable(bodyObject , bodyObject , min , max)
                }.first()
        }
    }
}