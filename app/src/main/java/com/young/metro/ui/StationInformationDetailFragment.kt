package com.young.metro.ui

import androidx.fragment.app.viewModels
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.base.BaseFragment
import com.young.metro.databinding.FragmentStationInformationDetailBinding
import com.young.presentation.viewmodel.DetailStationInformationViewModel

class StationInformationDetailFragment : BaseFragment<FragmentStationInformationDetailBinding , DetailStationInformationViewModel>() {
    override val layoutResource: Int = R.layout.fragment_station_information_detail
    override val viewModel: DetailStationInformationViewModel by viewModels()
    override val bindingVariable: Int = BR.vm

    override fun initBinding() {
        TODO("Not yet implemented")
    }

    override fun observerLiveData() {
        TODO("Not yet implemented")
    }

    override fun conversionFragment() {
        TODO("Not yet implemented")
    }
}