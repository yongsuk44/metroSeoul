package com.young.data_remote.repository

import com.young.data_remote.api.SeoulApiService
import com.young.data_remote.model.RemoteSubWayTel
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainSubWayTel
import com.young.domain.repository.information.remote.RemoteSubWayTelRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteSubWayTelRepositoryImpl @Inject constructor(
    private val service: SeoulApiService
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
}