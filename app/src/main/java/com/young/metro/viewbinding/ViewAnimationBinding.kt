package com.young.metro.viewbinding

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.bold
import androidx.databinding.BindingAdapter

@BindingAdapter("progressAnimation")
fun ProgressBar.setProgressBarAnimation(value : Int) {
    ValueAnimator.ofInt( 0 , value).apply {
        duration = 500
        addUpdateListener {
            this@setProgressBarAnimation.progress = it.animatedValue.toString().toInt()
        }
    }.start()
}

@BindingAdapter("progresBarApiAnimation")
fun ProgressBar.setProgressBarApiAnimation(value : Int) {
    ValueAnimator.ofInt( 0 , value).apply {
        duration = 500
        addUpdateListener {
            this@setProgressBarApiAnimation.progress = it.animatedValue.toString().toInt()
        }
    }.start()
}

@BindingAdapter("textValue" , "textSize" , "textType")
fun TextView.setTextPercent(percent : Int , textSize : Float , textType : String) {
    ValueAnimator.ofInt(0 , percent).apply {
        duration = 600
        addUpdateListener {
            this@setTextPercent.text = SpannableStringBuilder()
                .bold { append("${it.animatedValue}" , RelativeSizeSpan(textSize) , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }
                .append(textType)
        }
    }.start()
}

@BindingAdapter("textValue")
fun setTextCountAnimation(view : TextView , percent : Int ) {
    ValueAnimator.ofInt(0 , percent).apply {
        duration = 600
        addUpdateListener {
            view.text = it.animatedValue.toString()
        }
    }.start()
}

@BindingAdapter("textValue" , "textSize" , "type")
fun TextView.setTextNotAnimationPercent(percent : Int , textSize : Float , textType : String) {

    text = SpannableStringBuilder()
        .bold { append("$percent" , RelativeSizeSpan(textSize) , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE) }
        .append(textType)
}

fun ImageView.setImageRotationOpen() {
    ObjectAnimator.ofFloat(this , View.ROTATION , 0f , 180f).apply {
        duration = 200
    }.start()
}

fun ImageView.setImageRotationClose() {
    ObjectAnimator.ofFloat(this , View.ROTATION , 180f , 0f).apply {
        duration = 200
    }.start()
}