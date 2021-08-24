package com.young.cache.model

data class CacheSubwayFacilitiesPage(
    val currentCount: Int,
    val data: List<CacheSubwayFacilities>,
    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
)