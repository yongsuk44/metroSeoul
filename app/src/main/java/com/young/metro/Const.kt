package com.young.metro

import android.Manifest
import androidx.recyclerview.widget.DiffUtil

val locationPermissionList = arrayOf(
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION
)

val SimpleStringDiffCallBack = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem.length == newItem.length


    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

}