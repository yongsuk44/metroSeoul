package com.young.remote.repository

import com.young.remote.api.PublicDataPortalApiService
import com.young.remote.model.RemoteSubwayFacilities
import com.young.remote.model.RemoteSubwayFacilitiesPage
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.model.DomainSubwayFacilitiesPage
import com.young.domain.repository.subwayfacilities.RemoteSubWayFacilitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteSubWayFacilitiesRepositoryImpl @Inject constructor(
    private val service: PublicDataPortalApiService
) : RemoteSubWayFacilitiesRepository {
    override suspend fun getSubWayFacilitiesData(key: String): Flow<List<DomainSubwayFacilities>> =
        flowOf(service.getSubWayFacilitiesData(1, 400, key))
            .map {
                val mapper = BaseMapper(RemoteSubwayFacilities::class, DomainSubwayFacilities::class)

                BaseMapper<RemoteSubwayFacilitiesPage, DomainSubwayFacilitiesPage>().apply {
                    register("data", BaseMapper.setList(mapper))
                }.run {
                    this(it).data
                }
            }

}