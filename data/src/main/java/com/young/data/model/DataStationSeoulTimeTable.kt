package com.young.data.model

data class DataStationSeoulTimeTable(
    val SearchSTNTimeTableByIDService: DataSearchSTNTimeTableByIDService?
)

data class TimeTableRESULT(
    val CODE: String,
    val MESSAGE: String
)

data class DataTimeTableRow(
    val ARRIVETIME: String,
    val BRANCH_LINE: String,
    val DESTSTATION: String,
    val DESTSTATION2: String,
    val EXPRESS_YN: String,
    val FL_FLAG: String,
    val FR_CODE: String,
    val INOUT_TAG: String,
    val LEFTTIME: String,
    val LINE_NUM: String,
    val ORIGINSTATION: String,
    val STATION_CD: String,
    val STATION_NM: String,
    val SUBWAYENAME: String,
    val SUBWAYSNAME: String,
    val TRAIN_NO: String,
    val WEEK_TAG: String
)

data class DataSearchSTNTimeTableByIDService(
    val RESULT: TimeTableRESULT,
    val list_total_count: Int,
    val row: List<DataTimeTableRow>
)