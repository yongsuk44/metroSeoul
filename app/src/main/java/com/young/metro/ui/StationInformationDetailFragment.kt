package com.young.metro.ui

import android.content.Intent
import android.net.Uri
import android.transition.TransitionInflater
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.adapter.LineLogoSelectAdapter
import com.young.metro.adapter.TimeTableAdapter
import com.young.metro.base.BaseFragment
import com.young.metro.base.showToast
import com.young.metro.databinding.FragmentStationInformationDetailBinding
import com.young.metro.util.nowTimeNearList
import com.young.metro.util.recyclerViewScrollPosition
import com.young.metro.util.waitForTransition
import com.young.presentation.consts.DayType
import com.young.presentation.viewmodel.DetailDropBoxItemViewModel
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

    private val dropBoxItemViewModel: DetailDropBoxItemViewModel by viewModels()
    private val stationTimeTableViewModel : StationTimeTableViewModel by viewModels()

    private val lineLogoAdapter by lazy { LineLogoSelectAdapter(viewModel) }
    private val upTimeTableAdapter  by lazy { TimeTableAdapter() }
    private val downTimeTableAdapter  by lazy { TimeTableAdapter() }
    private val args: StationInformationDetailFragmentArgs by navArgs()

    override fun initBinding() {
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)

        viewDataBinding.timeTableViewModel = stationTimeTableViewModel
        viewDataBinding.rvDetailInformationStationLogo.adapter = lineLogoAdapter
        viewDataBinding.incTimetable.rvStationTimetableUpList.adapter = upTimeTableAdapter
        viewDataBinding.incTimetable.rvStationTimetableDownList.adapter = downTimeTableAdapter
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

        viewModel.selectLineCodePositionChange.observe(viewLifecycleOwner) {
            lineLogoAdapter.notifyDataSetChanged()
        }

        viewModel.selectStationLineData.observe(viewLifecycleOwner) {
            viewModel.getStationCodeToTelData(it.stinCd)
            stationTimeTableViewModel.changeDayCode(DayType.WEEK)
//            dropBoxItemViewModel.getConvenienceInformation(it.lnCd, it.railOprIsttCd, it.stinCd)
//            dropBoxItemViewModel.getPlatformEntranceData(it.railOprIsttCd, it.lnCd, it.stinCd)
        }

        viewModel.stationTelClick.observe(viewLifecycleOwner) {
            startActivity(
                Intent(Intent.ACTION_DIAL , Uri.parse("tel:$it"))
            )
        }

        stationTimeTableViewModel.dayCodeChangeData.observe(viewLifecycleOwner) {
            stationTimeTableViewModel.getStationTimeTable(viewModel.selectStationLineData.value , it)
        }

        stationTimeTableViewModel.timeTable.observe(viewLifecycleOwner) {
            when(it) {
                is SealedTimeTableData.Success -> {
                    viewDataBinding.incTimetable.timeTableData = it.data
                    upTimeTableAdapter.submitList(it.data?.up)
                    downTimeTableAdapter.submitList(it.data?.down)

                    viewDataBinding.incTimetable.rvStationTimetableUpList.recyclerViewScrollPosition(
                        lifecycleScope ,
                        it.data?.up?.nowTimeNearList() ?: 0
                    )

                    viewDataBinding.incTimetable.rvStationTimetableDownList.recyclerViewScrollPosition(
                        lifecycleScope,
                        it.data?.down?.nowTimeNearList() ?: 0
                    )
                }

                is SealedTimeTableData.Failed -> {
                    Timber.e(it.exception)
                    showToast("해당역에 대한 정보를 가져오지 못하였습니다.다시 시도해주세요.")
                }

                is SealedTimeTableData.Loading -> {
                    viewDataBinding.incTimetable.timeTableLoading = it.loading
                }
            }
        }
    }
}