package com.young.presentation.modelfunction

import com.young.presentation.model.StationEntranceBody

interface StationEntranceFunction {
    fun getConvenienceInformation(key: String, lineCode: String, trailCode: String, stationCode: String)

    /**
     * 지하철 입구에서 승강장 안내하는 사진 데이터를 BackStack 저장하기 위함
     */
    fun onMovePhotoView(item : List<StationEntranceBody>)

    /**
     * 상행, 하행으로 가는길 중 출입구 번호 아이템을 뿌리기 위함
     */
    fun onEntranceNumberList(item : List<Pair<String,List<StationEntranceBody>>>)

    /**
     * 지하철 입구에서 승강장 안내하는 이미지 및 안내문구 데이터 가져오기
     */
    fun getStationEntranceData(key: String, railCode: String, lineCd: String, stinCode: String)
}