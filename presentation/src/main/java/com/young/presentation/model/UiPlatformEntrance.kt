package com.young.presentation.model

data class UiStationEntrance(
    val upBody : List<Pair<String,List<StationEntranceBody>>>?,
    val upStationTitle : String?,
    val downBody : List<Pair<String,List<StationEntranceBody>>>?,
    val downStationTitle : String?,
    val header: Header
)

data class StationEntranceBody(
    val image : String ,
    val description : String
)