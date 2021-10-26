package com.young.metro.ui

import android.content.Intent
import android.net.Uri
import android.transition.TransitionInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.adapter.EntranceNumberAdapter
import com.young.metro.adapter.LineLogoSelectAdapter
import com.young.metro.adapter.TimeTableAdapter
import com.young.metro.base.BaseFragment
import com.young.metro.base.showToast
import com.young.metro.databinding.FragmentStationInformationDetailBinding
import com.young.metro.util.nowTimeNearList
import com.young.metro.util.recyclerViewScrollPosition
import com.young.metro.util.waitForTransition
import com.young.presentation.consts.BaseResult
import com.young.presentation.consts.DayType
import com.young.presentation.viewmodel.StationEntranceViewModel
import com.young.presentation.viewmodel.DetailStationInformationViewModel
import com.young.presentation.viewmodel.SealedTimeTableData
import com.young.presentation.viewmodel.StationTimeTableViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class StationInformationDetailFragment :
    BaseFragment<FragmentStationInformationDetailBinding, DetailStationInformationViewModel>() {
    override val layoutResource: Int = R.layout.fragment_station_information_detail
    override val viewModel: DetailStationInformationViewModel by viewModels()
    override val bindingVariable: Int = BR.vm

    private val stationEntranceViewModel: StationEntranceViewModel by viewModels()
    private val stationTimeTableViewModel: StationTimeTableViewModel by viewModels()

    private val lineLogoAdapter by lazy { LineLogoSelectAdapter(viewModel) }
    private val upTimeTableAdapter by lazy { TimeTableAdapter() }
    private val downTimeTableAdapter by lazy { TimeTableAdapter() }
    private val entranceNumberAdapter by lazy { EntranceNumberAdapter(stationEntranceViewModel) }
    private val args: StationInformationDetailFragmentArgs by navArgs()

    override fun initBinding() {
        sharedElementEnterTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        viewDataBinding.timeTableViewModel = stationTimeTableViewModel
        viewDataBinding.entranceViewModel = stationEntranceViewModel

        viewDataBinding.rvDetailInformationStationLogo.adapter = lineLogoAdapter
        viewDataBinding.stationName = args.stationName

        viewDataBinding.incEntrance.rvStationEntranceNumber.adapter = entranceNumberAdapter

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

        viewModel.selectLineCodePositionChange.observe(viewLifecycleOwner) {
            lineLogoAdapter.notifyDataSetChanged()
        }

        viewModel.selectStationLineData.observe(viewLifecycleOwner) {
            viewModel.getStationCodeToTelData(it.stinCd)

            stationEntranceViewModel.getStationEntranceData(
                getString(R.string.trailKey),
                it.railOprIsttCd,
                it.lnCd,
                it.stinCd
            )
        }

        viewModel.stationTelClick.observe(viewLifecycleOwner) {
            startActivity(
                Intent(Intent.ACTION_DIAL, Uri.parse("tel:$it"))
            )
        }

        stationTimeTableViewModel.timeTableOpen.observe(viewLifecycleOwner) {
            if (it) {
                viewDataBinding.incTimetable.rvStationTimetableUpList.adapter = upTimeTableAdapter
                viewDataBinding.incTimetable.rvStationTimetableDownList.adapter = downTimeTableAdapter

                stationTimeTableViewModel.changeDayCode(DayType.WEEK)
            }
        }

        stationTimeTableViewModel.dayCodeChangeData.observe(viewLifecycleOwner) {
            stationTimeTableViewModel.getStationTimeTable(
                viewModel.selectStationLineData.value,
                it,
                getString(R.string.seoulKey),
                getString(R.string.trailKey)
            )
        }

        stationTimeTableViewModel.timeTable.observe(viewLifecycleOwner) {
            when (it) {
                is SealedTimeTableData.Success -> {
                    viewDataBinding.incTimetable.timeTableData = it.data
                    upTimeTableAdapter.submitList(it.data?.up)
                    downTimeTableAdapter.submitList(it.data?.down)

                    viewDataBinding.incTimetable.rvStationTimetableUpList.recyclerViewScrollPosition(
                        lifecycleScope,
                        it.data?.up?.nowTimeNearList() ?: 0
                    )

                    viewDataBinding.incTimetable.rvStationTimetableDownList.recyclerViewScrollPosition(
                        lifecycleScope,
                        it.data?.down?.nowTimeNearList() ?: 0
                    )
                }

                is SealedTimeTableData.Failed -> {
                    Timber.e(it.exception)
                    showToast(getString(R.string.text_data_not_found))
                }

                is SealedTimeTableData.Loading -> {
                    viewDataBinding.incTimetable.timeTableLoading = it.loading
                }
            }
        }

        stationEntranceViewModel.stationEntranceData.observe(viewLifecycleOwner) {
            when (it) {
                is BaseResult.Failed -> {
                    Timber.e(it.exception)
                    showToast(getString(R.string.text_data_not_found))
                }
                is BaseResult.Loading -> {
                    viewDataBinding.incEntrance.loading = it.loading
                }
                is BaseResult.Success -> {
                    viewDataBinding.incEntrance.data = it.data
                }
            }
        }
        stationEntranceViewModel.stationEntranceNumberList.observe(viewLifecycleOwner) {
            entranceNumberAdapter.submitList(it)
        }

        stationEntranceViewModel.photoListData.observe(viewLifecycleOwner) {
            it?.let {
                with(findNavController().getBackStackEntry(R.id.stationInformationDetailFragment).savedStateHandle) {
                    set("photoData", it)
                }
                findNavController().navigate(R.id.action_stationInformationDetailFragment_to_photoListFragment)
            }
        }
    }
}