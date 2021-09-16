package com.young.metro.viewbinding

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.young.metro.adapter.LineLogoAdapter

@BindingAdapter("setDataCheckVisible")
fun setDataVisibleApply(view: View, data: Any?) {
    view.isVisible = when (data) {
        is String -> (data) != "" && data != null
        is Int -> data.ZeroOrNull()
        is List<*> -> !data.isNullOrEmpty()
        is Boolean -> data
        is Nothing -> true
        else -> false
    }
}

@BindingAdapter("onListAdapterSetting")
fun setAdapterSetting(view: RecyclerView, adapter: LineLogoAdapter) {
    view.adapter = adapter
}

fun Int?.ZeroOrNull() = this != null && this != 0
fun String.equlesDataToInt(value: String): Int = if (this == value) 1 else 0
