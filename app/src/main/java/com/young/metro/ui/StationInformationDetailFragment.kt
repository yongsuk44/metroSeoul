package com.young.metro.ui

import android.content.Intent
import android.net.Uri
import android.transition.TransitionInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.adapter.LineLogoSelectAdapter
import com.young.metro.base.BaseFragment
import com.young.metro.databinding.FragmentStationInformationDetailBinding
import com.young.metro.util.waitForTransition
import com.young.presentation.viewmodel.DetailDropBoxItemViewModel
import com.young.presentation.viewmodel.DetailStationInformationViewModel
import com.young.presentation.viewmodel.StationTimeTableViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class StationInformationDetailFragment :
    BaseFragment<FragmentStationInformationDetailBinding, DetailStationInformationViewModel>() {
    override val layoutResource: Int = R.layout.fragment_station_information_detail
    override val viewModel: DetailStationInformationViewModel by viewModels()
    override val bindingVariable: Int = BR.vm

    private val dropBoxItemViewModel: DetailDropBoxItemViewModel by viewModels()
    private val stationTimeTableViewModel : StationTimeTableViewModel by viewModels()

    private val lineLogoAdapter by lazy { LineLogoSelectAdapter(viewModel) }
    private val args: StationInformationDetailFragmentArgs by navArgs()

    override fun initBinding() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        viewDataBinding.timeTableViewModel = stationTimeTableViewModel
        viewDataBinding.rvDetailInformationStationLogo.adapter = lineLogoAdapter
        viewDataBinding.stationName = args.stationName

        viewModel.getStationData(args.stinCodes.toList())

        waitForTransition(viewDataBinding.incTimetable.loStationTimetable)
        waitForTransition(viewDataBinding.loDetailInformation)
        waitForTransition(viewDataBinding.rvDetailInformationStationLogo)
        waitForTransition(viewDataBinding.tvDetailInformationStationName)
    }

    override fun observerLiveData() {
        viewModel.selectStationLineListData.observe(viewLifecycleOwner) {
            lineLogoAdapter.submitList(it)
        }

        viewModel.stationSubData.observe(viewLifecycleOwner) {

        }

        viewModel.selectLineCodePositionChange.observe(viewLifecycleOwner) {
            lineLogoAdapter.notifyDataSetChanged()
        }

        viewModel.selectStationLineData.observe(viewLifecycleOwner) {
            viewModel.getStationCodeToTelData(it.stinCd)
//            dropBoxItemViewModel.getConvenienceInformation(it.lnCd, it.railOprIsttCd, it.stinCd)
            stationTimeTableViewModel.getStationTimeTable(it)
//            dropBoxItemViewModel.getPlatformAtTheEntranceData(it.railOprIsttCd, it.lnCd, it.stinCd)
        }

        viewModel.stationTelClick.observe(viewLifecycleOwner) {
            startActivity(
                Intent(Intent.ACTION_DIAL , Uri.parse("tel:$it"))
            )
        }
    }

}