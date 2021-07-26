package com.young.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.young.data.dao.FullRouteInformationDao
import com.young.data.model.LocalSubwayFacilities
import com.young.data.dao.SubWayFacilitiesDao
import com.young.data.model.FullRouteInformation
import com.young.data.model.LocalTrailCodeAndLineCode

@Database(
    entities = [LocalSubwayFacilities::class , FullRouteInformation::class , LocalTrailCodeAndLineCode::class] ,
    version = 1 ,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun subWayFacilitiesDao() : SubWayFacilitiesDao
    abstract fun fullRouteInformationDao() : FullRouteInformationDao
}