package com.young.data_remote.model

import java.util.*

data class RemoteSubwayFacilitiesPage(
    val currentCount: Int,
    val data: List<RemoteSubwayFacilities>,
    val matchCount: Int,
    val page: Int,
    val perPage: Int,
    val totalCount: Int
)