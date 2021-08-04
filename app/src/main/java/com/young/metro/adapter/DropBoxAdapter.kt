package com.young.metro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.young.metro.R
import com.young.metro.base.BaseViewHolder
import com.young.metro.databinding.ItemStationNameBinding
import com.young.presentation.model.AllRouteInformation
import com.young.presentation.viewmodel.FullRouteInformationViewModel

class DropBoxAdapter(
    val vm: FullRouteInformationViewModel
) : ListAdapter<AllRouteInformation , BaseViewHolder<ItemStationNameBinding>>(diffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemStationNameBinding> =
        BaseViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context) , R.layout.item_station_name , parent , false
            )
        )

    override fun onBindViewHolder(holder: BaseViewHolder<ItemStationNameBinding>, position: Int) {
        holder.binding.loItemStationName.animation = AnimationUtils.loadAnimation(holder.itemView.context , R.anim.anim_item_alpha)
        getItem(position).also {
            holder.binding.vm = vm
            holder.binding.data = it
            holder.binding.adapter = LineLogoAdapter().apply { submitList(it.lnCd) }
        }
    }

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<AllRouteInformation>() {
            override fun areItemsTheSame(oldItem: AllRouteInformation, newItem: AllRouteInformation): Boolean =
                oldItem.stinCd == newItem.stinCd

            override fun areContentsTheSame(oldItem: AllRouteInformation, newItem: AllRouteInformation): Boolean =
                oldItem == newItem
        }
    }
}