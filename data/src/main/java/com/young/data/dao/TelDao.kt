package com.young.data.dao

import androidx.room.*
import com.young.data.model.LocalStationTel

@Dao
interface TelDao{

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items : List<LocalStationTel>)

    @Query("select * from LocalStationTel where stinCd = :stinCd")
    fun getAllTelData(stinCd : String) : LocalStationTel
}