package com.young.metro.viewbinding

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import com.young.presentation.viewmodel.FullRouteInformationCase
import com.young.presentation.viewmodel.MainViewFunction

@BindingAdapter("customTextWatcher")
fun setCustomTextWatcher(view : EditText , listener : FullRouteInformationCase?) {
    view.doAfterTextChanged {
        listener?.onChangeTextValue(it.toString())
    }
}