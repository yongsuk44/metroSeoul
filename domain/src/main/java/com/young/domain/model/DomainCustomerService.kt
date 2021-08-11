package com.young.domain.model

data class DomainCustomerService(
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