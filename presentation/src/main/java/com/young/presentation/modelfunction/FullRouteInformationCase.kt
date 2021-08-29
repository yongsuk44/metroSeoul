package com.young.presentation.modelfunction

import com.young.presentation.model.ListRouteInformation

interface FullRouteInformationCase {
    fun loadFullRouteInformation()
    suspend fun getStationRouteInformation(key: String)
    suspend fun getCacheFullRouteInformation()
    fun insertAllStationCodes()
    fun onSearchEditViewClick(value : Boolean)
    fun onStationClick(item : ListRouteInformation , position : Int)
    fun onSearchEditViewClear()
}