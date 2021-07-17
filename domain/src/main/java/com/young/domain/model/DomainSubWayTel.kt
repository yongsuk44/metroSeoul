package com.young.domain.model

import com.google.gson.annotations.SerializedName

data class DomainSubWayTel(
    @SerializedName("엘리베이터 동영상")
    val ELEVATER: String,
    @SerializedName("엘리베이터 자막")
    val ELEVATER_TXT: String,
    @SerializedName("출구 정보")
    val EXIT_INFO: String,
    @SerializedName("편의시설 아이콘 이미지 목록")
    val ICON_PATH: String,
    @SerializedName("노선 명칭")
    val LINE_NAME: String,
    @SerializedName("고유 번호")
    val NO: String,
    @SerializedName("역 명칭")
    val STATION: String,
    @SerializedName("역 이미지")
    val STATION_IMAGE: String,
    @SerializedName("지하철 팝업 이미지")
    val STATION_IMAGE2: String,
    @SerializedName("전화번호")
    val TELNO_INFO: String,
    @SerializedName("코스1 첫차")
    val TIME_INFO1: String,
    @SerializedName("코스1 막차")
    val TIME_INFO2: String,
    @SerializedName("코스2 첫차")
    val TIME_INFO3: String,
    @SerializedName("코스2 막차")
    val TIME_INFO4: String,
    val USEYN: String
)