package com.young.data_remote.repository

import com.young.data_remote.api.PublicDataPortalApiService
import com.young.data_remote.api.SeoulApiService
import com.young.data_remote.model.Data
import com.young.data_remote.model.RemoteCustomerService
import com.young.data_remote.model.RemoteSubWayTel
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainCustomerService
import com.young.domain.model.DomainSubWayTel
import com.young.domain.repository.informationdetail.remote.RemoteSubWayTelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import java.lang.NullPointerException
import javax.inject.Inject

class RemoteSubWayTelRepositoryImpl @Inject constructor(
    private val service: SeoulApiService,
    private val publicDataPortalApiService: PublicDataPortalApiService
) : RemoteSubWayTelRepository {

    override suspend fun getSubWayTelData(key: String): Flow<List<DomainSubWayTel>> =
        flowOf(service.getSubWayTelData(key))
            .map {
                it.subwayTourInfo.row
            }.map {
                val mapper = BaseMapper(RemoteSubWayTel::class, DomainSubWayTel::class)
                BaseMapper.setList(mapper).run {
                    this(it)
                }
            }

    override suspend fun getStationTelData(
        apiService: String,
        key: String
    ): Flow<DomainCustomerService> {
        val uddi: String = when (apiService) {
            "15041410" -> "04acebd2-9dd7-49b3-b144-84770ff645bd"
            "15041422" -> "eed2014e-3641-466d-aad6-ef95c0fc1aaf"
            "15041421" -> "89a93518-dcf6-458b-a5e1-c2f487c37ff1"
            "15041420" -> "8f02333b-5bd8-4ed4-ab80-e0e532817340"
            "15041419" -> "f5b5ab47-8fdf-4d8e-b79e-b1baf9f7e8a5"
            "15041418" -> "8afa3ffd-d760-4d48-bfee-8b2c59fbfb03"
            "15041417" -> "9a4283be-da17-4993-b3a4-dcb3775412f2"
            "15041416" -> "48e607f3-b6f3-4da4-9cf9-9c074a7213b1"
            "15041415" -> "d8e7c7f7-b636-402a-8e65-f1cb7b0f61b3"
            "15041414" -> "b4e441b3-2f86-4b7a-9e3d-8156b79d0d24"
            else -> ""
        }

        if (uddi.isEmpty()) throw NullPointerException("해당 라인에 API 요청 값이 없음 : $apiService")
        else {
            return flowOf(
                publicDataPortalApiService.getStationLineTelData(
                    apiService, uddi, 1, 200, key
                )
            )
                .map {
                    val mapper = BaseMapper(Data::class, com.young.domain.model.Data::class)

                    BaseMapper(RemoteCustomerService::class, DomainCustomerService::class).apply {
                        register("data", BaseMapper.setList(mapper))
                    }.run {
                        this(it)
                    }
                }
        }
    }

}