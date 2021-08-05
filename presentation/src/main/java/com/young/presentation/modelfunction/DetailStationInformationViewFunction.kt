package com.young.presentation.modelfunction

import com.young.domain.model.DomainPlatformEntrance
import kotlinx.coroutines.flow.Flow

interface DetailStationInformationViewFunction {

    fun getStationData(stinCodes : List<String>)
    fun getSubWayTelData()
    fun onLinePositionClick(position : Int)

}