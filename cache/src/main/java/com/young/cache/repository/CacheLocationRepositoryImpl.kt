package com.young.cache.repository

import androidx.datastore.core.DataStore
import com.young.cache.Metro
import com.young.data.datasource.cache.CacheLocationDataSource
import com.young.data.model.DataUserLocationData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CacheLocationRepositoryImpl @Inject constructor(
    private val locationStore: DataStore<Metro.LocationStore>
) : CacheLocationDataSource {

    override suspend fun readLastLocation(): Flow<DataUserLocationData> =
        locationStore.data.map { location -> DataUserLocationData(location.latitude, location.longitude) }

    override suspend fun updateLastLocation(userLocationData: DataUserLocationData): Flow<DataUserLocationData> = flow {
        locationStore.updateData { location ->
            location.toBuilder()
                .setLatitude(userLocationData.latitude)
                .setLongitude(userLocationData.longitude)
                .build()
        }.also { location ->
            emit(DataUserLocationData(location.latitude, location.longitude))
        }
    }

}