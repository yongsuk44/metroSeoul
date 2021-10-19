package com.young.metro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.young.metro.R
import com.young.metro.base.BaseViewHolder
import com.young.metro.databinding.ItemLocationNearStationBinding
import com.young.presentation.model.UiStationNameDistance
import com.young.presentation.viewmodel.LocationViewModel

class LocationNearAdapter(
    val vm : LocationViewModel
) : ListAdapter<UiStationNameDistance , BaseViewHolder<ItemLocationNearStationBinding>>(diffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemLocationNearStationBinding> {
        return  BaseViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_location_near_station, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ItemLocationNearStationBinding>, position: Int) {
        holder.binding.loItemLocationNearStation.animation = AnimationUtils.loadAnimation(holder.itemView.context , R.anim.anim_item_scale_alpha)
        holder.binding.vm = vm
        holder.binding.data = getItem(position)
        holder.binding.adapter = LineLogoAdapter().apply { submitList(getItem(position).lineCode) }
        holder.binding.position = position
    }

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<UiStationNameDistance>() {
            override fun areItemsTheSame(oldItem: UiStationNameDistance, newItem: UiStationNameDistance): Boolean =
                oldItem.stationName == newItem.stationName

            override fun areContentsTheSame(oldItem: UiStationNameDistance, newItem: UiStationNameDistance): Boolean =
                oldItem == newItem
        }
    }
}