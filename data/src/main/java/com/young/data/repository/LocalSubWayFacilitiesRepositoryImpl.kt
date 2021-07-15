package com.young.data.repository

import com.young.data.dao.SubWayFacilitiesDao
import com.young.data.model.LocalSubwayFacilities
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.DomainSubwayFacilities
import com.young.domain.repository.subwayfacilities.LocalSubWayFacilitiesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalSubWayFacilitiesRepositoryImpl @Inject constructor(
    private val service : SubWayFacilitiesDao
) : LocalSubWayFacilitiesRepository {
    override suspend fun getLineMetroData(lineNumber: String): Flow<List<DomainSubwayFacilities>> = flow {
        service.getLineMetroData(lineNumber)
    }

    override suspend fun insertSubWayFacilitiesDataAll(items: List<DomainSubwayFacilities>) {
        BaseMapper.setList(BaseMapper<DomainSubwayFacilities , LocalSubwayFacilities>()).run {
            service.insertSubWayFacilitiesData(
                this(items)
            )
        }
    }

    override suspend fun getStationData(stationNumber: String): Flow<DomainSubwayFacilities> = flow {
        service.getStationData(stationNumber)
    }

    override suspend fun updateSubWayFacilitiesData(items: List<DomainSubwayFacilities>) {
        BaseMapper.setList(BaseMapper<DomainSubwayFacilities , LocalSubwayFacilities>()).run {
            service.update(
                this(items)
            )
        }
    }
}