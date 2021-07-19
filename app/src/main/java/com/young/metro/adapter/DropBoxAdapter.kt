package com.young.metro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.young.metro.R
import com.young.metro.base.BaseViewHolder
import com.young.metro.databinding.ItemStationNameBinding
import com.young.presentation.viewmodel.MainViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DropBoxAdapter(
    val vm: MainViewModel
) : ListAdapter<String , BaseViewHolder<ItemStationNameBinding>>(diffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ItemStationNameBinding> =
        BaseViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context) , R.layout.item_station_name , parent , false
            )
        )

    override fun onBindViewHolder(holder: BaseViewHolder<ItemStationNameBinding>, position: Int) {
        getItem(position).also {
            holder.binding.vm = vm
        }
    }

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem.length == newItem.length

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }
}