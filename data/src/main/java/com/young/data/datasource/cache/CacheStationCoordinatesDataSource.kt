package com.young.data.datasource.cache

import com.young.data.model.DataStationNameAndMapXY
import kotlinx.coroutines.flow.Flow

interface CacheStationCoordinatesDataSource {
    suspend fun insertStationCoordinateData(items : List<DataStationNameAndMapXY>)
    suspend fun getStationCoordinateAllData() : Flow<List<DataStationNameAndMapXY>>
    suspend fun getStationCoordinateDataSize() : Flow<Int>
    suspend fun getLocationNearStationList(lastX : Double , lastY :Double , km : Double) : Flow<List<DataStationNameAndMapXY>>
}