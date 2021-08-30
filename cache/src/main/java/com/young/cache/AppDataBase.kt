package com.young.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.young.cache.dao.AllStationCodeDao
import com.young.cache.dao.FullRouteInformationDao
import com.young.cache.dao.LocationDao
import com.young.cache.model.CacheAllStationCodes
import com.young.cache.model.CacheFullRouteInformation
import com.young.cache.model.CacheStationNameAndMapXY
import com.young.cache.model.CacheTrailCodeAndLineCode

@Database(
    entities = [
        CacheFullRouteInformation::class,
        CacheTrailCodeAndLineCode::class,
        CacheStationNameAndMapXY::class,
        CacheAllStationCodes::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun fullRouteInformationDao(): FullRouteInformationDao
    abstract fun locationDao(): LocationDao
    abstract fun allStationCodeDao() : AllStationCodeDao
}