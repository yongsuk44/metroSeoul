package com.young.metro.viewbinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.young.metro.R
import com.young.metro.adapter.LineLogoAdapter
import com.young.metro.adapter.TimeTableAdapter
import com.young.metro.ui.custom.CircleTextView

@BindingAdapter("CircleTextViewSelectedBackground")
fun setCircleTextViewSelectedBackground(view : CircleTextView , check : Boolean) {
    if (check) {
        view.setCustomBackgroundColor(R.color.black)
        view.setTextColor(view.resources.getColor(R.color.white , null))
    } else {
        view.setCustomBackgroundColor(R.color.white)
        view.setTextColor(view.resources.getColor(R.color.black , null))
    }
}

@BindingAdapter("timeTableListAdapter" , "timeTableList")
fun setTimeTableListAdapter(view: RecyclerView, adapter: TimeTableAdapter , list : List<String>) {
    adapter.submitList(list)
}