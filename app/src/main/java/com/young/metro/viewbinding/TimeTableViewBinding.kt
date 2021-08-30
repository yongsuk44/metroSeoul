package com.young.metro.viewbinding

import androidx.databinding.BindingAdapter
import com.young.metro.R
import com.young.metro.ui.custom.CircleTextView

@BindingAdapter("timeTableDayBoxCheckBackground")
fun setTimeTableDayBoxCheckBackground(view : CircleTextView , check : Boolean) {
    if (check) {
        view.setCustomBackgroundColor(R.color.black)
        view.setTextColor(view.resources.getColor(R.color.white , null))
    } else {
        view.setCustomBackgroundColor(R.color.white)
        view.setTextColor(view.resources.getColor(R.color.black , null))
    }
}