package com.young.presentation.model

import com.google.gson.annotations.SerializedName

data class UiCustomerService(
    val currentCount: Int,
    val data : List<Data>,
    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
)

data class Data(
    val stationName: String,
    val telNumber: String?
)