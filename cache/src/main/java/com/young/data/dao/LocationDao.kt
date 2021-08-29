package com.young.data.dao

import androidx.room.*
import com.young.data.model.CacheStationNameAndMapXY

@Dao
interface LocationDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStationCoordinatesData(items : List<CacheStationNameAndMapXY>)

    @Query("select * from CacheStationNameAndMapXY")
    suspend fun getStationCoordinateAllData() : List<CacheStationNameAndMapXY>

    @Query("select count() from CacheStationNameAndMapXY")
    suspend fun getStationCoordinateDataSize() : Int

    @Query(
        "select * ,((:cosX * localCosX) * ((localCosY * :cosY) + localSinY * :sinY) + (:sinX * localSinX)) as partial_distance from CacheStationNameAndMapXY where partial_distance >= :area order by partial_distance desc"
    )
    suspend fun getNearStationData(cosX : Double , cosY : Double , sinX : Double , sinY : Double ,area : Double) : List<CacheStationNameAndMapXY>
}