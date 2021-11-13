package com.young.metro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.young.metro.R
import com.young.metro.base.BaseViewHolder
import com.young.metro.databinding.ItemStationEntranceNumberBinding
import com.young.presentation.model.StationEntranceBody
import com.young.presentation.viewmodel.StationEntranceViewModel

class EntranceNumberAdapter(val vm : StationEntranceViewModel) : ListAdapter<Pair<String,List<StationEntranceBody>> , BaseViewHolder<ItemStationEntranceNumberBinding>>(EntranceNumberDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemStationEntranceNumberBinding> =
        BaseViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context) , R.layout.item_station_entrance_number , parent , false))

    override fun onBindViewHolder(holder: BaseViewHolder<ItemStationEntranceNumberBinding>, position: Int) {
        holder.binding.vm = vm
        holder.binding.key = getItem(position).first
        holder.binding.data = getItem(position)
    }
}

val EntranceNumberDiffCallback = object : DiffUtil.ItemCallback<Pair<String,List<StationEntranceBody>>>() {
    override fun areItemsTheSame(oldItem: Pair<String,List<StationEntranceBody>>, newItem: Pair<String,List<StationEntranceBody>>): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(oldItem: Pair<String,List<StationEntranceBody>>, newItem: Pair<String,List<StationEntranceBody>>): Boolean =
        oldItem == newItem
}