package com.young.metro

import androidx.recyclerview.widget.DiffUtil

val SimpleStringDiffCallBack = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem.length == newItem.length


    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
        oldItem == newItem

}