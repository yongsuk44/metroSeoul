package com.young.data_remote.repository

import com.young.data_remote.api.TrailPorTalService
import com.young.domain.repository.information.remote.RemoteLocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemoteLocationRepositoryImpl @Inject constructor(
    private val service: TrailPorTalService
) : RemoteLocationRepository {
    override suspend fun getStationAddress(
        key: String,
        trailCode: String,
        lineCode: String
    ): Flow<List<String>> =
        flowOf(service.getStationAddressData(key, "json", trailCode, lineCode).body)
            .map {
                it.map { it.roadNmAdr }
            }
            .flowOn(Dispatchers.IO)


}