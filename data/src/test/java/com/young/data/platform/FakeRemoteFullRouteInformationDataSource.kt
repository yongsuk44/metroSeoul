package com.young.data.platform

import com.young.data.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.data.model.DataConvenienceInformation
import com.young.data.model.DataFullRouteInformation
import com.young.data.model.DataStationEntrance
import com.young.data.model.DataRow
import kotlinx.coroutines.flow.Flow

interface FakeRemoteFullRouteInformationDataSource : RemoteFullRouteInformationDataSource {
    override suspend fun getStationRouteInformation(key: String): Flow<DataFullRouteInformation> {
        TODO("Not yet implemented")
    }

    override suspend fun getConvenienceInformation(
        key: String,
        lineCode: String,
        trailCode: String,
        stationCode: String
    ): Flow<DataConvenienceInformation> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllStationCode(seoulKey: String): Flow<List<DataRow>> {
        TODO("Not yet implemented")
    }

    override suspend fun getStationEntranceData(
        key: String,
        railCode: String,
        lineCd: String,
        stinCode: String
    ): Flow<DataStationEntrance> {
        TODO("Not yet implemented")
    }
}