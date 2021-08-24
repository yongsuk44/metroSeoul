package com.young.cache.dao

import androidx.room.*
import com.young.cache.model.CacheFullRouteInformation
import com.young.cache.model.CacheTrailCodeAndLineCode

@Dao
interface FullRouteInformationDao {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubWayFacilitiesData(items : List<CacheFullRouteInformation>)

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLineCodeAndTrailCode(items : List<CacheTrailCodeAndLineCode>)


    @Query("select * from CacheTrailCodeAndLineCode")
    suspend fun getTrailCodeAllData() : List<CacheTrailCodeAndLineCode>

    @Query("select * from CacheFullRouteInformation")
    suspend fun getAllData() : List<CacheFullRouteInformation>

    @Query("select count() from CacheFullRouteInformation")
    suspend fun getDataSize() : Int

    @Query("select * from CacheFullRouteInformation where stinCd in (:stinCodes)")
    suspend fun getStationData(stinCodes : List<String>) : List<CacheFullRouteInformation>


    @Query("select * from CacheFullRouteInformation where stinNm = :stationName")
    suspend fun getStationNameToFullRouteInformationData(stationName : String) : CacheFullRouteInformation


}