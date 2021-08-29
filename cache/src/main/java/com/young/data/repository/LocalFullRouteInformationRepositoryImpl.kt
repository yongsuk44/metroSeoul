package com.young.data.repository

import com.young.data.dao.FullRouteInformationDao
import com.young.data.mapper.CacheToDomainMapper.DomainToLocal
import com.young.data.mapper.CacheToDomainMapper.LocalToDomain
import com.young.data.model.LocalTrailCodeAndLineCode
import com.young.domain.mapper.BaseMapper
import com.young.domain.model.AllRouteInformation
import com.young.domain.model.DomainTrailCodeAndLineCode
import com.young.domain.repository.location.LocalFullRouteInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalFullRouteInformationRepositoryImpl @Inject constructor(
    private val dao: FullRouteInformationDao
) : LocalFullRouteInformationRepository {
    override suspend fun insert(param: List<AllRouteInformation>) {

        val data = flowOf(param)
            .map {
                it.map {
                    it.DomainToLocal()
                }
            }.single()

        dao.insertSubWayFacilitiesData(data)

        data.map {
            it.localTrailCodeAndLineCode
        }.distinctBy {
            it.lnCd to it.railOprIsttCd
        }.run {
            dao.insertLineCodeAndTrailCode(this.toList())
        }
    }

    override suspend fun getAllData(): Flow<List<AllRouteInformation>> =
        flowOf(dao.getAllData())
            .map {
                it.map { it.LocalToDomain() }
            }

    override suspend fun getDataSize(): Flow<Int> =
        flowOf(dao.getDataSize())

    override suspend fun getTrailCodeAllData(): Flow<List<DomainTrailCodeAndLineCode>> =
        flowOf(dao.getTrailCodeAllData())
            .map {
                BaseMapper.setList(BaseMapper<LocalTrailCodeAndLineCode, DomainTrailCodeAndLineCode>())
                    .run {
                        this(it)
                    }
            }

    override suspend fun getStationData(stinCode : List<String>): Flow<List<AllRouteInformation>> =
        flowOf(dao.getStationData(stinCode))
            .map {
                it.map { it.LocalToDomain() }
            }

    override suspend fun getStationNameToFullRouteInformationData(name: String): Flow<AllRouteInformation> =
        flowOf(dao.getStationNameToFullRouteInformationData(name))
            .map {
                BaseMapper<CacheFullRouteInformation , AllRouteInformation>().run { this(it) }
            }

}