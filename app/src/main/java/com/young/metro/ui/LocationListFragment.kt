package com.young.metro.ui

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.adapter.LocationNearAdapter
import com.young.metro.base.BaseFragment
import com.young.metro.databinding.FragmentLocationListBinding
import com.young.metro.util.toTransitionGroup
import com.young.metro.util.waitForTransition
import com.young.presentation.consts.EventObserver
import com.young.presentation.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LocationListFragment : BaseFragment<FragmentLocationListBinding>() {
    override val layoutResource: Int = R.layout.fragment_location_list
    override val bindingVariable: Int = BR.vm

    private val adapter : LocationNearAdapter by lazy { LocationNearAdapter(viewModel) }
    val viewModel: LocationViewModel by viewModels()

    override fun initBinding() {
        viewDataBinding.apply {
            vm = viewModel
            rvLocationNearStation.adapter = this@LocationListFragment.adapter
        }

        viewModel.loadStationData(Firebase.database)
    }

    override fun observerLiveData() {
        viewModel.stationNameAndMapXY.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            viewModel.setLoadingValue(false)
            waitForTransition(viewDataBinding.rvLocationNearStation)
        }

        viewModel.stationClick.observe(viewLifecycleOwner , EventObserver {
            val holderBinding = viewDataBinding.rvLocationNearStation.findViewHolderForLayoutPosition(viewModel.selectPosition.value ?: 0)?.itemView ?: return@EventObserver

            val extras = FragmentNavigatorExtras(
                holderBinding.findViewById<ConstraintLayout>(R.id.lo_item_location_near_station).toTransitionGroup(),
                holderBinding.findViewById<RecyclerView>(R.id.rv_item_location_near_line_logo).toTransitionGroup(),
                holderBinding.findViewById<TextView>(R.id.tv_item_location_near_station_name).toTransitionGroup()
            )

            findNavController().navigate(
                LocationListFragmentDirections.actionLocationListFragmentToStationInformationDetailFragment(it.stinCd.toTypedArray() , it.stationName),
                extras
            )
        })
    }
}