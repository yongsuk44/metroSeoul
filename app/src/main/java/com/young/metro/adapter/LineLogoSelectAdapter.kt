package com.young.metro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.young.metro.R
import com.young.metro.base.BaseViewHolder
import com.young.metro.databinding.ItemLineLogoBinding
import com.young.metro.databinding.ItemLineLogoSelectBinding
import com.young.presentation.model.IndexAllRouteInformation
import com.young.presentation.viewmodel.DetailStationInformationViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
class LineLogoSelectAdapter(
    val vm: DetailStationInformationViewModel
) : ListAdapter<IndexAllRouteInformation, BaseViewHolder<ItemLineLogoSelectBinding>>(diffCallBack) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ItemLineLogoSelectBinding> =
        BaseViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_line_logo_select, parent, false
            )
        )

    override fun onBindViewHolder(holder: BaseViewHolder<ItemLineLogoSelectBinding>, position: Int) {
        holder.binding.vm = vm
        holder.binding.data = getItem(position).lnCd
        holder.binding.selectCheck = vm.selectLineCodePositionChange.value == position
        holder.binding.position = position
    }

    companion object {
        val diffCallBack = object : DiffUtil.ItemCallback<IndexAllRouteInformation>() {
            override fun areItemsTheSame(oldItem: IndexAllRouteInformation, newItem: IndexAllRouteInformation): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: IndexAllRouteInformation, newItem: IndexAllRouteInformation): Boolean =
                oldItem == newItem
        }
    }
}