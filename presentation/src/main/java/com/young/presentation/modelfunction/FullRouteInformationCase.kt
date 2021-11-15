package com.young.presentation.modelfunction

import com.young.domain.model.DomainFullRouteInformationBody
import com.young.presentation.model.ListRouteInformation
import kotlinx.coroutines.flow.Flow

interface FullRouteInformationCase {
    fun loadFullRouteInformation(trailKey : String)
    suspend fun getRemoteFullRouteInformation(key: String) : Flow<List<DomainFullRouteInformationBody>>
    suspend fun getCacheFullRouteInformation() : Flow<List<DomainFullRouteInformationBody>>
    fun insertFullRouteInformationDataAndTrailLineCode(list : List<DomainFullRouteInformationBody>)
    fun insertAllStationCodes(seoulKey : String)
    fun onSearchEditViewClick(value : Boolean)
    fun onStationClick(item : ListRouteInformation)
    fun onSearchEditViewClear()
}