package com.young.base

import android.Manifest

const val TAG = "SeoulMetroTest"

val locationPermissionList = arrayOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION
)

const val FireBaseStationLocationKey = "StationLocationData"