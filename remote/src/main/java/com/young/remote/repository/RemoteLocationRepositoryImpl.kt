package com.young.remote.repository

import com.young.remote.api.TrailPorTalService
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.LocationData
import com.young.domain.repository.remote.RemoteLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteLocationRepositoryImpl @Inject constructor(
    private val service: TrailPorTalService
) : RemoteLocationRepository {
    override suspend fun getStationAddress(
        key: String,
        trailCode: String,
        lineCode: String
    ): Flow<List<LocationData>> =
        flowOf(
            service.getStationAddressData(key, "json", trailCode, lineCode).body
        ).map {
            BaseMapper.setList(BaseMapper<com.young.remote.model.LocationData, LocationData>()).run {
                this(it)
            }
        }
}