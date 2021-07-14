package com.young.data.repository

import com.young.data.dao.SubWayFacilitiesDao
import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import java.util.*
import javax.inject.Inject

class LocalSubWayFacilitiesRepositoryImpl @Inject constructor(
    private val service : SubWayFacilitiesDao
) : LocalSubWayFacilitiesRepository {
    override suspend fun getLineMetroData(lineNumber: String): List<DomainSubwayFacilities> {
        TODO("Not yet implemented")
    }

    override suspend fun insertSubWayFacilitiesDataAll(items: List<DomainSubwayFacilities>): List<Long> {
        TODO("Not yet implemented")
    }

    override suspend fun getStationData(stationNumber: String): DomainSubwayFacilities {
        TODO("Not yet implemented")
    }

    override suspend fun updateSubWayFacilitiesData(items: List<DomainSubwayFacilities>): List<Long> {
        TODO("Not yet implemented")
    }
}