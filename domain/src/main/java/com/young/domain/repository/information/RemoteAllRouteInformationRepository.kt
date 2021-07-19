package com.young.domain.repository.information

import com.young.domain.model.DomainAllRouteInformation
import kotlinx.coroutines.flow.Flow

interface RemoteFullRouteInformationRepository {
    suspend fun getFullRouteInformation(key : String , lineCode: String) : Flow<DomainAllRouteInformation>
}