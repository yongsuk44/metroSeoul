package com.young.metro.viewbinding

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter

@BindingAdapter("setDataCheckVisible")
fun setDataVisibleApply(view: View, data: Any?) {
    view.isVisible = when (data) {
        is String -> (data) != "" && data != null
        is Int -> data.ZeroOrNull()
        is List<*> -> !data.isNullOrEmpty()
        is Boolean -> data
        else -> false
    }
}

fun Int?.ZeroOrNull() = this != null && this != 0
fun String.equlesDataToInt(value : String) : Int = if (this == value) 1 else 0