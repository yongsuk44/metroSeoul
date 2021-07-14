package com.young.presentation.model

data class UiSubwayFacilitiesPage(
    val currentCount: Int,
    val data: List<UiSubwayFacilities>,
    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
)