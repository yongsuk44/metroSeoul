package com.young.data.datasource.remote

import com.young.data.model.DataConvenienceInformation
import com.young.data.model.DataFullRouteInformation
import com.young.data.model.DataStationEntrance
import com.young.data.model.DataRow
import com.young.domain.model.DomainFullRouteInformationBody
import kotlinx.coroutines.flow.Flow

interface RemoteFullRouteInformationDataSource {
    suspend fun getConvenienceInformation(key: String, lineCode: String, trailCode: String, stationCode: String) : Flow<DataConvenienceInformation>
    suspend fun getAllStationCode(seoulKey: String) : Flow<List<DataRow>>
    suspend fun getStationEntranceData(key : String , railCode : String ,lineCd : String, stinCode : String) : Flow<DataStationEntrance>

    suspend fun findStationRouteInformation(key : String?) : Flow<DataFullRouteInformation>
}