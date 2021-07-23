package com.young.data.repository

import com.young.data.dao.FullRouteInformationDao
import com.young.data.mapper.LocalToDomainMapper.DomainToLocal
import com.young.data.mapper.LocalToDomainMapper.LocalToDomain
import com.young.data.model.FullRouteInformation
import com.young.domain.model.AllRouteInformation
import com.young.domain.repository.information.local.LocalFullRouteInformationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalFullRouteInformationRepositoryImpl @Inject constructor(
    private val dao : FullRouteInformationDao
) : LocalFullRouteInformationRepository {
    override suspend fun insert(param: List<AllRouteInformation>) {
        dao.insertSubWayFacilitiesData(param.DomainToLocal())
    }

    override suspend fun getAllData() : Flow<List<AllRouteInformation>> =
        flowOf(dao.getAllData())
            .map {
                it.LocalToDomain()
            }

    override suspend fun getDataSize(): Int =
        dao.getDataSize()

}