package com.young.data.model

import java.util.*

data class LocalSubwayFacilitiesPage(
    val currentCount: Int,
    val data: List<LocalSubwayFacilities>,
    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
)