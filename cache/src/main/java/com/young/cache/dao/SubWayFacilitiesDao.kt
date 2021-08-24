package com.young.cache.dao

import androidx.room.*
import com.young.cache.model.CacheSubwayFacilities

@Dao
interface SubWayFacilitiesDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubWayFacilitiesData(items : List<CacheSubwayFacilities>)

    @Query("select * from CacheSubwayFacilities where station_number = :stationNumber")
    suspend fun getStationData(stationNumber : String) : CacheSubwayFacilities

    @Query("select * from CacheSubwayFacilities where metro_linenumber == :lineNumber")
    suspend fun getLineMetroData(lineNumber : String) : List<CacheSubwayFacilities>

    @Query("select count() from CacheSubwayFacilities")
    suspend fun getDataSize() : Int

    @Transaction
    @Update
    suspend fun update(items : List<CacheSubwayFacilities>)

    @Query("select * from CacheSubwayFacilities")
    suspend fun getAllData() : List<CacheSubwayFacilities>
}