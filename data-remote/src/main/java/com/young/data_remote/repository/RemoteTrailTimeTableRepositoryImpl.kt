package com.young.data_remote.repository

import com.young.data_remote.api.TrailPorTalService
import com.young.data_remote.model.Header
import com.young.data_remote.model.RemoteTrailTimeTable
import com.young.data_remote.model.TimeTableBody
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainTrailTimeTable
import com.young.domain.repository.information.remote.RemoteTrailTimeTableRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteTrailTimeTableRepositoryImpl @Inject constructor(
    private val service: TrailPorTalService
) : RemoteTrailTimeTableRepository {
    override suspend fun getTrailTimetables(
        key: String,
        railCode: String,
        dayCd: String,
        lineCode: String,
        stationCode: String
    ): Flow<DomainTrailTimeTable> =
        flowOf(
            service.getTrailTimetables(
                key = key,
                format = "json",
                trailCode = railCode,
                toDayCode = dayCd,
                lineCode = lineCode,
                stationCode = stationCode
            )
        ).flowOn(Dispatchers.IO).map {
            val timeTableMapper = BaseMapper(RemoteTrailTimeTable::class, DomainTrailTimeTable::class)
            val headerMapper = BaseMapper(Header::class, com.young.domain.model.Header::class)
            val timeTableBody = BaseMapper(TimeTableBody::class, com.young.domain.model.TimeTableBody::class)

            timeTableMapper.apply {
                register("header", headerMapper)
                register("body", BaseMapper.setList(timeTableBody))
            }.run {
                this(it)
            }
        }.flowOn(Dispatchers.Default)
}