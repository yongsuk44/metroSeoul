package com.young.domain.repository.information.local

import com.young.domain.model.AllRouteInformation
import kotlinx.coroutines.flow.Flow

interface LocalFullRouteInformationRepository {
    suspend fun insert(param : List<AllRouteInformation>)
    suspend fun getAllData() : Flow<List<AllRouteInformation>>
    suspend fun getDataSize() : Int
}