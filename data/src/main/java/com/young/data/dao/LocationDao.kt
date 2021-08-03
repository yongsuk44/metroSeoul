package com.young.data.dao

import androidx.room.*
import com.young.data.model.LocalStationNameAndMapXY
import kotlin.math.cos

@Dao
interface LocationDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStationCoordinatesData(items : List<LocalStationNameAndMapXY>)

    @Query("select * from LocalStationNameAndMapXY")
    suspend fun getStationCoordinateAllData() : List<LocalStationNameAndMapXY>

    @Query("select count() from LocalStationNameAndMapXY")
    suspend fun getStationCoordinateDataSize() : Int

    @Query(
        "select * ,((:cosX * localCosX) * ((localCosY * :cosY) + localSinY * :sinY) + (:sinX * localSinX)) as partial_distance from LocalStationNameAndMapXY where partial_distance >= :area order by partial_distance desc"
    )
    suspend fun getNearStationData(cosX : Double , cosY : Double , sinX : Double , sinY : Double ,area : Double) : List<LocalStationNameAndMapXY>
}