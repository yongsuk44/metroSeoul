package com.young.presentation.modelfunction

import com.young.presentation.model.ListRouteInformation

interface FullRouteInformationCase {
    fun loadFullRouteInformation()
    suspend fun getFullRouteInformation(key: String)
    suspend fun getLocalFullRouteInformation()

    fun onSearchEditViewClick(value : Boolean)
    fun onStationClick(item : ListRouteInformation , position : Int)
    fun onSearchEditViewClear()
}