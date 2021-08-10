package com.young.presentation.modelfunction

interface DetailStationInformationViewFunction {

    fun getStationData(stinCodes : List<String>)
    fun getStationSubData(lineCd : String , stationName : String)
    fun onLinePositionClick(position : Int)
    fun onStationCallClick()
}