package com.young.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.young.data.model.LocalSubwayFacilities
import com.young.data.dao.SubWayFacilitiesDao

@Database(
    entities = [LocalSubwayFacilities::class] ,
    version = 1 ,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun subWayFacilitiesDao() : SubWayFacilitiesDao
}