package com.young.metro.viewbinding

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("StringPlusMeter")
fun setStringPlusMeter(view : TextView , data : Double) {
    view.text = "${data.toInt()}미터"
}