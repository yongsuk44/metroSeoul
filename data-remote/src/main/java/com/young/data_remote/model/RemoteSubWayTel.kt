package com.young.data_remote.model

import com.google.gson.annotations.SerializedName

data class RemoteSubWayTelPage(
    val subwayTourInfo: SubwayTourInfo
)

data class SubwayTourInfo(
    val RESULT: RESULT,
    val row: List<RemoteSubWayTel>
)

data class RESULT(
    val CODE: String,
    val MESSAGE: String
)

data class RemoteSubWayTel(
    //엘리베이터 동영상
    val ELEVATER: String,
    //엘리베이터 자막
    val ELEVATER_TXT: String,
    //출구 정보
    val EXIT_INFO: String,
    //편의시설 아이콘 이미지 목록
    val ICON_PATH: String,
    //노선 명칭
    val LINE_NAME: String,
    //고유 번호
    val NO: String,
    //역 명칭
    val STATION: String,
    //역 이미지
    val STATION_IMAGE: String,
    //지하철 팝업 이미지
    val STATION_IMAGE2: String,
    //전화번호
    val TELNO_INFO: String,
    //코스1 첫차
    val TIME_INFO1: String,
    //코스1 막차
    val TIME_INFO2: String,
    //코스2 첫차
    val TIME_INFO3: String,
    //코스2 막차
    val TIME_INFO4: String,
    val USEYN: String,

    val COURSE1: String,
    val COURSE2: String,
    val LINE: String
)