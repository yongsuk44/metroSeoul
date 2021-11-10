package com.young.metro.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.young.metro.R
import com.young.metro.SimpleStringDiffCallBack
import com.young.metro.base.BaseViewHolder
import com.young.metro.databinding.ItemStationEntranceGuideDescriptionBinding

class EntranceGuideDescriptionAdapter :
    ListAdapter<String, BaseViewHolder<ItemStationEntranceGuideDescriptionBinding>>(
        SimpleStringDiffCallBack
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ItemStationEntranceGuideDescriptionBinding> =
        BaseViewHolder(
            DataBindingUtil.inflate<ItemStationEntranceGuideDescriptionBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_station_entrance_guide_description,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: BaseViewHolder<ItemStationEntranceGuideDescriptionBinding>, position: Int) {
        holder.binding.txt = getItem(position)
    }

}