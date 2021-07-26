package com.young.data_remote.repository

import com.young.data_remote.api.TrailPorTalService
import com.young.data_remote.mapper.RemoteToDomainMapper.RemoteToDomain
import com.young.data_remote.model.AllRouteInformation
import com.young.data_remote.model.Header
import com.young.data_remote.model.RemoteAllRouteInformation
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainAllRouteInformation
import com.young.domain.model.DomainConvenienceInformation
import com.young.domain.repository.information.remote.RemoteFullRouteInformationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class RemoteFullRouteInformationRepositoryImpl @Inject constructor(
    private val service: TrailPorTalService
) : RemoteFullRouteInformationRepository {
    override suspend fun getFullRouteInformation(
        key: String
    ): Flow<DomainAllRouteInformation> =
        flowOf(service.getFullRouteInformation(key, "json", "01"))
            .map {
                val timeTableMapper =
                    com.young.domain.mapper.BaseMapper(
                        com.young.data_remote.model.RemoteAllRouteInformation::class,
                        com.young.domain.model.DomainAllRouteInformation::class
                    )
                val headerMapper = com.young.domain.mapper.BaseMapper(
                    com.young.data_remote.model.Header::class,
                    com.young.domain.model.Header::class
                )
                val bodyMapper = com.young.domain.mapper.BaseMapper(
                    com.young.data_remote.model.AllRouteInformation::class,
                    com.young.domain.model.AllRouteInformation::class
                )

                timeTableMapper.apply {
                    register("header", headerMapper)
                    register("body", com.young.domain.mapper.BaseMapper.setList(bodyMapper))
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
            }.flowOn(Dispatchers.IO)
}