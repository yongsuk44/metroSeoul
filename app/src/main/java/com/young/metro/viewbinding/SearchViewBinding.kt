package com.young.metro.viewbinding

import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.young.presentation.modelfunction.CustomFocusChange

@BindingAdapter("app:searchEditTextFocus")
fun getSearchEditTextFocus(view : EditText , listener : CustomFocusChange) {
    view.setOnFocusChangeListener { _, b ->
        listener.onFocusChange(b)
    }
}