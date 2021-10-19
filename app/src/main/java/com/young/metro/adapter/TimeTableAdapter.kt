package com.young.metro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.young.metro.R
import com.young.metro.SimpleStringDiffCallBack
import com.young.metro.base.BaseViewHolder
import com.young.metro.databinding.ItemSimpleStationTimeTxtBinding

class TimeTableAdapter : ListAdapter<String , BaseViewHolder<ItemSimpleStationTimeTxtBinding>>(SimpleStringDiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemSimpleStationTimeTxtBinding> =
        BaseViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context) , R.layout.item_simple_station_time_txt , parent , false))

    override fun onBindViewHolder(holder: BaseViewHolder<ItemSimpleStationTimeTxtBinding>, position: Int) {
        holder.binding.tvItemSimpleTimeTableTxt.animation = AnimationUtils.loadAnimation(holder.itemView.context , R.anim.anim_item_scale)
        holder.binding.time = getItem(position)
    }
}