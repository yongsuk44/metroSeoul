package com.young.data.dao

import androidx.room.*
import com.young.data.model.FullRouteInformation
import com.young.data.model.LocalTrailCodeAndLineCode
import kotlinx.coroutines.flow.Flow

@Dao
interface FullRouteInformationDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubWayFacilitiesData(items : List<FullRouteInformation>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLineCodeAndTrailCode(items : List<LocalTrailCodeAndLineCode>)

    @Query("select * from LocalTrailCodeAndLineCode")
    suspend fun getTrailCodeAllData() : List<LocalTrailCodeAndLineCode>

    @Query("select * from FullRouteInformation")
    suspend fun getAllData() : List<FullRouteInformation>

    @Query("select count() from FullRouteInformation")
    suspend fun getDataSize() : Int
}