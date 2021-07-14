package com.young.data_remote.repository

import com.young.data_remote.api.SubwayFacilitiesService
import com.young.data_remote.model.RemoteSubwayFacilities
import com.young.data_remote.model.RemoteSubwayFacilitiesPage
import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.model.DomainSubwayFacilitiesPage
import com.young.domain.repository.subwayfacilities.RemoteSubWayFacilitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import com.young.domain.mapper.BaseMapper

class RemoteSubWayFacilitiesRepositoryImpl @Inject constructor(
    private val service: SubwayFacilitiesService
) : RemoteSubWayFacilitiesRepository {
    override suspend fun getSubWayFacilitiesData(key: String): Flow<List<DomainSubwayFacilities>> = flow {
        val mapper = BaseMapper(RemoteSubwayFacilities::class, DomainSubwayFacilities::class)

        BaseMapper<RemoteSubwayFacilitiesPage, DomainSubwayFacilitiesPage>().apply {
            register("data", BaseMapper.setList(mapper))
        }.run {
            emit(
                this(service.getSubWayFacilitiesData(1, 300, key)).data
            )
        }
    }
}