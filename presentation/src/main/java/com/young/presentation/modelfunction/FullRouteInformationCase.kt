package com.young.presentation.modelfunction

interface FullRouteInformationCase {
    fun loadSubWayTelData(key: String)
    suspend fun getSubWayTelData(key: String)

    fun loadFullRouteInformation(key: String , lineCode: String)
    suspend fun getFullRouteInformation(key: String, lineCode: String)
}