package com.young.data.dao

import androidx.room.*
import com.young.data.model.LocalStationNameAndMapXY

@Dao
interface LocationDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStationCoordinatesData(items : List<LocalStationNameAndMapXY>)

    @Query("select * from LocalStationNameAndMapXY")
    suspend fun getStationCoordinateAllData() : List<LocalStationNameAndMapXY>

    @Query("select count() from LocalStationNameAndMapXY")
    suspend fun getStationCoordinateDataSize() : Int
}