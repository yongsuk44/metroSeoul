package com.young.data_remote.model

import com.google.gson.annotations.SerializedName

data class RemoteCustomerService(
    val currentCount: Int,
    val data : List<Data>,
    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
)

data class Data(
    @SerializedName("역명")
    val stationName: String,
    @SerializedName("전화번호")
    val telNumber: String?
)