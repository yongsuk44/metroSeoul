package com.young.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.young.data.dao.AllStationCodeDao
import com.young.data.dao.FullRouteInformationDao
import com.young.data.dao.LocationDao
import com.young.data.dao.SubWayFacilitiesDao
import com.young.data.model.*

@Database(
    entities = [
        CacheSubwayFacilities::class,
        CacheFullRouteInformation::class,
        CacheTrailCodeAndLineCode::class,
        CacheStationNameAndMapXY::class,
        CacheAllStationCodes::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun subWayFacilitiesDao(): SubWayFacilitiesDao
    abstract fun fullRouteInformationDao(): FullRouteInformationDao
    abstract fun locationDao(): LocationDao
    abstract fun allStationCodeDao() : AllStationCodeDao
}