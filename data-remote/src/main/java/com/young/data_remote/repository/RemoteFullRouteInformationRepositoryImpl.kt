package com.young.data_remote.repository

import com.young.data_remote.api.TrailPorTalService
import com.young.data_remote.mapper.RemoteToDomainMapper.RemoteToDomain
import com.young.data_remote.model.AllRouteInformation
import com.young.data_remote.model.Header
import com.young.data_remote.model.RemoteAllRouteInformation
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainAllRouteInformation
import com.young.domain.model.DomainConvenienceInformation
import com.young.domain.repository.information.RemoteFullRouteInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteFullRouteInformationRepositoryImpl @Inject constructor(
    private val service: TrailPorTalService
) : RemoteFullRouteInformationRepository {
    override suspend fun getFullRouteInformation(
        key: String,
        lineCode: String
    ): Flow<DomainAllRouteInformation> =
        flowOf(service.getFullRouteInformation(key, "json", "01", lineCode))
            .map {
                val timeTableMapper =
                    BaseMapper(RemoteAllRouteInformation::class, DomainAllRouteInformation::class)
                val headerMapper = BaseMapper(Header::class, com.young.domain.model.Header::class)
                val bodyMapper = BaseMapper(
                    AllRouteInformation::class,
                    com.young.domain.model.AllRouteInformation::class
                )

                timeTableMapper.apply {
                    register("header", headerMapper)
                    register("body", BaseMapper.setList(bodyMapper))
                }.run {
                    this(it)
                }
            }

    override suspend fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    ): Flow<DomainConvenienceInformation> =
        flowOf(service.getConvenienceInformation(key , "json" , lineCode, trailCode, stationCode))
            .map {
                it.RemoteToDomain()
            }
}