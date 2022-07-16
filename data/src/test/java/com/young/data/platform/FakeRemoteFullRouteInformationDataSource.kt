package com.young.data.platform

import com.young.data.datasource.remote.RemoteFullRouteInformationDataSource
import com.young.data.model.DataConvenienceInformation
import com.young.data.model.DataFullRouteInformation
import com.young.data.model.DataStationEntrance
import com.young.data.model.DataRow
import kotlinx.coroutines.flow.Flow

interface FakeRemoteFullRouteInformationDataSource : RemoteFullRouteInformationDataSource {

}