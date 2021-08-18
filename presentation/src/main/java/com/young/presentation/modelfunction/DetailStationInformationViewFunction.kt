package com.young.presentation.modelfunction

interface DetailStationInformationViewFunction {

    fun getStationData(stinCodes : List<String>)
    fun getStationCodeToTelData(stationCode : String)
    fun onLinePositionClick(position : Int)
    fun onStationCallClick()
}