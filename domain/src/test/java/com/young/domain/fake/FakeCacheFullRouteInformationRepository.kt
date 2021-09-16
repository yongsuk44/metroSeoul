package com.young.domain.fake

import com.young.domain.model.DomainFullRouteInformationBody
import com.young.domain.model.DomainTrailCodeAndLineCode
import com.young.domain.repository.location.CacheFullRouteInformationRepository
import kotlinx.coroutines.flow.Flow

interface FakeCacheFullRouteInformationRepository : CacheFullRouteInformationRepository {
    override suspend fun insert(param: List<DomainFullRouteInformationBody>): Flow<List<Long>> {
        TODO("Not yet implemented")
    }

    override suspend fun insertLineCodeAndTrailCode(param: List<DomainTrailCodeAndLineCode>): Flow<List<Long>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllData(): Flow<List<DomainFullRouteInformationBody>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDataSize(): Flow<Int> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrailCodeAllData(): Flow<List<DomainTrailCodeAndLineCode>> {
        TODO("Not yet implemented")
    }

    override suspend fun getStationData(stinCode: List<String>): Flow<List<DomainFullRouteInformationBody>> {
        TODO("Not yet implemented")
    }

    override suspend fun getStationNameToFullRouteInformationData(name: String): Flow<DomainFullRouteInformationBody> {
        TODO("Not yet implemented")
    }
}