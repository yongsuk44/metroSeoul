package com.young.metro.ui

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import android.transition.ChangeBounds
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.adapter.LineLogoAdapter
import com.young.metro.adapter.LineLogoSelectAdapter
import com.young.metro.base.BaseFragment
import com.young.metro.databinding.FragmentStationInformationDetailBinding
import com.young.metro.util.waitForTransition
import com.young.presentation.viewmodel.DetailDropBoxItemViewModel
import com.young.presentation.viewmodel.DetailStationInformationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class StationInformationDetailFragment : BaseFragment<FragmentStationInformationDetailBinding , DetailStationInformationViewModel>() {
    override val layoutResource: Int = R.layout.fragment_station_information_detail
    override val viewModel: DetailStationInformationViewModel by viewModels()
    override val bindingVariable: Int = BR.vm

    private val dropBoxItemViewModel : DetailDropBoxItemViewModel by viewModels()
    private val lineLogoAdapter by lazy { LineLogoSelectAdapter(viewModel) }
    private val args : StationInformationDetailFragmentArgs by navArgs()

    override fun initBinding() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        viewDataBinding.stationName = args.stationName
        viewModel.getStationData(args.stinCodes.toList())
        viewDataBinding.rvDetailInformationStationLogo.adapter = lineLogoAdapter

        waitForTransition(viewDataBinding.rvDetailInformationStationLogo)
        waitForTransition(viewDataBinding.tvDetailInformationStationName)

        viewDataBinding.executePendingBindings()
    }

    override fun observerLiveData() {
        viewModel.selectStationData.observe(viewLifecycleOwner) {
            it?.let {
                lineLogoAdapter.submitList(it)
                viewModel.getSubWayTelData()

                it.first().also {
                    dropBoxItemViewModel.getConvenienceInformation(it.lnCd , it.railOprIsttCd , it.stinCd)
                    dropBoxItemViewModel.getTrailTimetables(it.railOprIsttCd , "6" , it.lnCd , it.stinCd)
                    dropBoxItemViewModel.getPlatformAtTheEntranceData(it.railOprIsttCd , it.lnCd, it.stinCd)
                }
            }
        }

        viewModel.selectLineCodePositionChange.observe(viewLifecycleOwner) {
            lineLogoAdapter.notifyDataSetChanged()
        }
    }

}