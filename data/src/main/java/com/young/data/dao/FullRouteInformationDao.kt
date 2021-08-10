package com.young.data.dao

import androidx.room.*
import com.young.data.model.FullRouteInformation
import com.young.data.model.LocalStationNameAndMapXY
import com.young.data.model.LocalTrailCodeAndLineCode
import com.young.domain.model.AllRouteInformation
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

    @Query("select * from FullRouteInformation where stinCd in (:stinCodes)")
    suspend fun getStationData(stinCodes : List<String>) : List<FullRouteInformation>


    @Query("select * from FullRouteInformation where stinNm = :stationName")
    suspend fun getStationNameToFullRouteInformationData(stationName : String) : FullRouteInformation


}