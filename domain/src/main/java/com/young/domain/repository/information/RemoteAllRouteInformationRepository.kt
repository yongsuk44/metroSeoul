package com.young.domain.repository.information

import com.young.domain.model.DomainAllRouteInformation
import com.young.domain.model.DomainConvenienceInformation
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query

interface RemoteFullRouteInformationRepository {
    suspend fun getFullRouteInformation(key : String , lineCode: String) : Flow<DomainAllRouteInformation>
    suspend fun getConvenienceInformation(key: String, lineCode: String, trailCode: String, stationCode: String) : Flow<DomainConvenienceInformation>
}