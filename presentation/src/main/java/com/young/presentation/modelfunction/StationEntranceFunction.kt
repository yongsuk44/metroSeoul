package com.young.presentation.modelfunction

import com.young.presentation.model.StationEntranceBody

interface StationEntranceFunction {
    fun getConvenienceInformation(key: String, lineCode: String, trailCode: String, stationCode: String)
    /**
     *
     */
    fun onEntranceGuideOpen()

    /**
     * 지하철 입구에서 승강장 안내하는 가이드 보기
     */
    fun onEntranceGuideView(item: Pair<String , List<StationEntranceBody>>?)

    /**
     *
     */
    fun onGuidePhotoMove(photoUrl : String)

    /**
     * 상행, 하행으로 가는길 중 출입구 번호 아이템을 뿌리기 위함
     */
    fun onEntranceNumberList(item : List<Pair<String,List<StationEntranceBody>>>)

    /**
     * 지하철 입구에서 승강장 안내하는 이미지 및 안내문구 데이터 가져오기
     */
    fun getStationEntranceData(key: String, railCode: String, lineCd: String, stinCode: String)
}