package com.young.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.young.data.model.LocalAllStationCodes

@Dao
interface AllStationCodeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items : List<LocalAllStationCodes>) : List<Long>

    @Query("select * from LocalAllStationCodes where FR_CODE = :code")
    fun findStationCodes(code : String) : LocalAllStationCodes?

    @Query("select * from LocalAllStationCodes")
    fun getAllStationCodeData() : List<LocalAllStationCodes>
}