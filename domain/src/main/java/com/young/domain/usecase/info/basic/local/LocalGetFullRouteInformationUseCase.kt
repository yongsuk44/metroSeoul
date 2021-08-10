package com.young.domain.usecase.info.basic.local

import com.young.domain.model.AllRouteInformation
import com.young.domain.repository.information.local.LocalFullRouteInformationRepository
import com.young.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LocalGetFullRouteInformationBaseUseCase {
    suspend fun getAllData() : Flow<List<AllRouteInformation>>
    suspend fun getDataSize() : Flow<Int>
    suspend fun getStationNameToFullRouteInformationData(name : String) : Flow<AllRouteInformation>
}

class LocalGetFullRouteInformationUseCase @Inject constructor(
    private val local : LocalFullRouteInformationRepository
) : LocalGetFullRouteInformationBaseUseCase {
    override suspend fun getAllData(): Flow<List<AllRouteInformation>> =
        local.getAllData()

    override suspend fun getDataSize() : Flow<Int> =
        local.getDataSize()

    override suspend fun getStationNameToFullRouteInformationData(name: String): Flow<AllRouteInformation> =
        local.getStationNameToFullRouteInformationData(name)

}