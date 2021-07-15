package com.young.data_remote.repository

import android.net.Uri
import com.young.data_remote.api.SubwayFacilitiesService
import com.young.data_remote.model.RemoteSubwayFacilities
import com.young.data_remote.model.RemoteSubwayFacilitiesPage
import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.model.DomainSubwayFacilitiesPage
import com.young.domain.repository.subwayfacilities.RemoteSubWayFacilitiesRepository
import javax.inject.Inject
import com.young.domain.mapper.BaseMapper
import kotlinx.coroutines.flow.*
import java.net.URLEncoder

class RemoteSubWayFacilitiesRepositoryImpl @Inject constructor(
    private val service: SubwayFacilitiesService
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