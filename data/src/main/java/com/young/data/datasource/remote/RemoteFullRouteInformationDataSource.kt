package com.young.data.datasource.remote

import com.young.data.model.DataConvenienceInformation
import com.young.data.model.DataFullRouteInformation
import com.young.data.model.DataPlatformEntrance
import com.young.data.model.DataRow
import kotlinx.coroutines.flow.Flow

interface RemoteFullRouteInformationDataSource {
    suspend fun getStationRouteInformation(key : String) : Flow<DataFullRouteInformation>
    suspend fun getConvenienceInformation(key: String, lineCode: String, trailCode: String, stationCode: String) : Flow<DataConvenienceInformation>
    suspend fun getAllStationCode(seoulKey: String) : Flow<List<DataRow>>
    suspend fun getPlatformEntranceData(key : String , railCode : String ,lineCd : String, stinCode : String) : Flow<DataPlatformEntrance>
}