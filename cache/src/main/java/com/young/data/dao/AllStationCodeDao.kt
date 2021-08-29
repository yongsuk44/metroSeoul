package com.young.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.young.data.model.CacheAllStationCodes

@Dao
interface AllStationCodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items : List<CacheAllStationCodes>) : List<Long>

    @Query("select * from CacheAllStationCodes where FR_CODE = :code")
    fun findStationCodes(code : String) : CacheAllStationCodes?

    @Query("select * from CacheAllStationCodes")
    fun getAllStationCodeData() : List<CacheAllStationCodes>
}