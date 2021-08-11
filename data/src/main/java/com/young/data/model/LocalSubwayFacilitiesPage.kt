package com.young.data.model

data class LocalSubwayFacilitiesPage(
    val currentCount: Int,
    val data: List<LocalSubwayFacilities>,
    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
)