package com.young.domain.model

import java.util.*

data class DomainSubwayFacilitiesPage(
    val currentCount: Int,
    val data: List<DomainSubwayFacilities>,
    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
)