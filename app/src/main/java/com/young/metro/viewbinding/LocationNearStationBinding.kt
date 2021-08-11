package com.young.metro.viewbinding

import android.animation.ValueAnimator
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.young.metro.R
import com.young.metro.ui.custom.CircleTextView

@BindingAdapter("StringPlusMeter")
fun setStringPlusMeter(view : TextView , data : Double) {

    ValueAnimator.ofInt(0 , data.toInt()).apply {
        duration = 600
        addUpdateListener {
            view.text = "${it.animatedValue}미터"
        }
    }.start()
}

@BindingAdapter("LocationRadiusClickCheck")
fun setLocationRadiusPositionCheck(view : CircleTextView , check : Boolean) {
    if (check) {
        view.setCustomBackgroundColor(R.color.black)
        view.setTextColor(view.context.getColor(R.color.white))
    } else {
        view.setCustomBackgroundColor(R.color.white)
        view.setTextColor(view.context.getColor(R.color.black))
    }
}