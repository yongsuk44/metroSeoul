package com.young.metro.ui.custom

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.appcompat.widget.AppCompatTextView
import com.young.metro.R
import com.young.metro.util.equalsZeroCheck

class CircleTextView(
    context: Context,
    attributes: AttributeSet
) : AppCompatTextView(context, attributes) {

    init {
        initialize(context, attributes)
    }

    fun initialize(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleTextView)

        GradientDrawable().apply {

            typedArray.getDimension(R.styleable.CircleTextView_cornerRadius, 0f).run {
                cornerRadius = equalsZeroCheck(this) ?: return@run
            }

            typedArray.getColor(R.styleable.CircleTextView_customBackgroundColor, resources.getColor(R.color.black, null)).run {
                gravity = Gravity.CENTER
                setColor(this)
            }

            setStroke(
                typedArray.getDimensionPixelSize(R.styleable.CircleTextView_customStrokeWidth , 0) ,
                typedArray.getColor(R.styleable.CircleTextView_customStrokeColor ,0)
            )

        }.run {
            typedArray.recycle()
            this@CircleTextView.background = this
        }
    }

    fun setCustomBackgroundColor(@ColorRes value: Int) {
        (background as GradientDrawable).setColor(
            resources.getColor(value, null)
        )
    }


    fun setCustomStrokeColor(@ColorRes value : Int , @DimenRes width : Int) {
        (background as GradientDrawable).setStroke(
            resources.getDimensionPixelSize(width) ,
            resources.getColor(value, null)
        )
    }
}