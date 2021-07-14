package com.young.data.dao

import androidx.room.*
import com.young.data.model.LocalSubwayFacilities
import java.util.*

@Dao
interface SubWayFacilitiesDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubWayFacilitiesData(items : List<LocalSubwayFacilities>)

    @Query("select * from LocalSubwayFacilities where station_number = :stationNumber")
    suspend fun getStationData(stationNumber : String) : LocalSubwayFacilities

    @Query("select * from LocalSubwayFacilities where metro_linenumber == :lineNumber")
    suspend fun getLineMetroData(lineNumber : String) : List<LocalSubwayFacilities>

    @Transaction
    @Update
    suspend fun update(items : List<LocalSubwayFacilities>)
}