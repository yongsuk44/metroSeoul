package com.young.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.young.cache.dao.AllStationCodeDao
import com.young.cache.dao.FullRouteInformationDao
import com.young.cache.dao.LocationDao
import com.young.cache.dao.SubWayFacilitiesDao
import com.young.cache.model.*

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