package com.young.metro.ui.custom

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.young.metro.R
import com.young.metro.util.equalsZeroCheck

class CustomConstraintLayout(context: Context, attrs: AttributeSet) :
    ConstraintLayout(context, attrs) {

    init {
        initialize(context, attrs)
    }


    fun initialize(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomConstraintLayout)
        GradientDrawable().apply {

            cornerRadii = floatArrayOf(
                typedArray.getDimension(R.styleable.CustomConstraintLayout_cornerTopLeftRadius , 0f) ,
                typedArray.getDimension(R.styleable.CustomConstraintLayout_cornerTopLeftRadius , 0f) ,
                typedArray.getDimension(R.styleable.CustomConstraintLayout_cornerTopRightRadius , 0f) ,
                typedArray.getDimension(R.styleable.CustomConstraintLayout_cornerTopRightRadius , 0f) ,
                typedArray.getDimension(R.styleable.CustomConstraintLayout_cornerBottomRightRadius , 0f) ,
                typedArray.getDimension(R.styleable.CustomConstraintLayout_cornerBottomRightRadius , 0f) ,
                typedArray.getDimension(R.styleable.CustomConstraintLayout_cornerBottomLeftRadius , 0f) ,
                typedArray.getDimension(R.styleable.CustomConstraintLayout_cornerBottomLeftRadius , 0f)
            )

            typedArray.getDimension(R.styleable.CustomConstraintLayout_cornerRadius, 0f).run {
                cornerRadius = equalsZeroCheck(this) ?: return@run
            }

            setColor(
                typedArray.getColor(
                    R.styleable.CustomConstraintLayout_customBackgroundColor,
                    resources.getColor(R.color.white, null)
                )
            )

            setStroke(
                typedArray.getDimensionPixelSize(R.styleable.CustomConstraintLayout_customStrokeWidth , 0) ,
                typedArray.getColor(R.styleable.CustomConstraintLayout_customStrokeColor ,0)
            )

        }.run {
            typedArray.recycle()
            this@CustomConstraintLayout.background = this
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