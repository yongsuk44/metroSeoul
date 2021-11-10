package com.young.metro.viewbinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.young.metro.R

@BindingAdapter("entranceGuideImage")
fun setEntranceGuideImage(view : ImageView , url : String?) {
    url?.let {
        Glide.with(view)
            .load(url)
            .sizeMultiplier(1f)
            .error(R.drawable.close_circle_outline)
            .into(view)
    }
}