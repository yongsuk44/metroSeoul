package com.young.metro.viewbinding

import androidx.databinding.BindingAdapter
import com.young.metro.R
import com.young.metro.ui.custom.CircleImageView

@BindingAdapter("setSelectCustomBackground")
fun setSelectBackground(view : CircleImageView , check : Boolean) {
    if (check) view.setCustomBackgroundColor(R.color.colorTextGray)
    else view.setCustomBackgroundColor(R.color.white)
}