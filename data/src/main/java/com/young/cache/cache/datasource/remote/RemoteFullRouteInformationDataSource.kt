package com.young.cache.cache.datasource.remote

import com.young.cache.cache.model.DataConvenienceInformation
import com.young.cache.cache.model.DataFullRouteInformation
import com.young.cache.cache.model.DataPlatformEntrance
import com.young.cache.cache.model.DataRow
import kotlinx.coroutines.flow.Flow

interface RemoteFullRouteInformationDataSource {
    suspend fun getStationRouteInformation(key : String) : Flow<DataFullRouteInformation>
    suspend fun getConvenienceInformation(key: String, lineCode: String, trailCode: String, stationCode: String) : Flow<DataConvenienceInformation>
    suspend fun getAllStationCode(seoulKey: String) : Flow<List<DataRow>>
    suspend fun getPlatformEntranceData(key : String , railCode : String ,lineCd : String, stinCode : String) : Flow<DataPlatformEntrance>
}