package com.young.metro.ui

import androidx.fragment.app.viewModels
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.adapter.LineLogoAdapter
import com.young.metro.base.BaseFragment
import com.young.metro.databinding.FragmentStationInformationDetailBinding
import com.young.presentation.viewmodel.DetailStationInformationViewModel
import com.young.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StationInformationDetailFragment : BaseFragment<FragmentStationInformationDetailBinding , DetailStationInformationViewModel>() {
    override val layoutResource: Int = R.layout.fragment_station_information_detail
    override val viewModel: DetailStationInformationViewModel by viewModels()
    override val bindingVariable: Int = BR.vm

    private val lineLogoAdapter by lazy { LineLogoAdapter() }

    override fun initBinding() {
        viewDataBinding.rvDetailInformationStationLogo.adapter = lineLogoAdapter
        viewModel.getStationData(listOf("133" ,"426", "A01", "P313"))
    }

    override fun observerLiveData() {
        viewModel.selectStationData.observe(viewLifecycleOwner) {
            lineLogoAdapter.submitList(it.lnCd)
        }
    }
}