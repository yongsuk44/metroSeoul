package com.young.data.dao

import androidx.room.*
import com.young.data.model.FullRouteInformation

@Dao
interface FullRouteInformationDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubWayFacilitiesData(items : List<FullRouteInformation>)

    @Query("select * from FullRouteInformation")
    suspend fun getAllData() : List<FullRouteInformation>

    @Query("select count() from FullRouteInformation")
    suspend fun getDataSize() : Int
}