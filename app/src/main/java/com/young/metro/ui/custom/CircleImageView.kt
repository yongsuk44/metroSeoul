package com.young.metro.ui.custom

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.appcompat.widget.AppCompatImageView
import com.young.metro.R

class CircleImageView(
    context: Context,
    attributes: AttributeSet
) : AppCompatImageView(context, attributes) {

    init {
        initialize(context, attributes)
    }

    fun initialize(context: Context, attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)

        GradientDrawable().apply {
            cornerRadius = typedArray.getDimension(R.styleable.CircleImageView_cornerRadius, 0f)
            setColor(
                typedArray.getColor(
                    R.styleable.CircleImageView_customBackgroundColor,
                    resources.getColor(R.color.white, null)
                )
            )
            setStroke(
                typedArray.getDimensionPixelSize(R.styleable.CircleImageView_customStrokeWidth , 0) ,
                typedArray.getColor(R.styleable.CircleImageView_customStrokeColor ,0)
            )

        }.run {
            typedArray.recycle()
            this@CircleImageView.background = this
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