package com.young.metro.ui

import android.location.Geocoder
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.adapter.LocationNearAdapter
import com.young.metro.base.BaseFragment
import com.young.metro.base.showToast
import com.young.metro.databinding.FragmentLocationListBinding
import com.young.metro.util.toTransitionGroup
import com.young.metro.util.waitForTransition
import com.young.presentation.consts.EventObserver
import com.young.presentation.viewmodel.LocationCurrentViewModel
import com.young.presentation.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.util.*

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LocationListFragment : BaseFragment<FragmentLocationListBinding, LocationViewModel>() {
    override val layoutResource: Int = R.layout.fragment_location_list
    override val viewModel: LocationViewModel by viewModels()
    override val bindingVariable: Int = BR.vm

    private val adapter : LocationNearAdapter by lazy { LocationNearAdapter(viewModel) }
    private val currentViewModel : LocationCurrentViewModel by viewModels()

    override fun initBinding() {
        getLastLocationServiceData()

        viewDataBinding.ivLocationListRefresh.setOnClickListener { getLastLocationServiceData() }
        viewDataBinding.rvLocationNearStation.adapter = this@LocationListFragment.adapter
    }

    override fun observerLiveData() {

        viewModel.stationCoordinateDataSize.observe(viewLifecycleOwner) {
            it?.let {
                if (it > 0) viewModel.onLocationRadiusData(viewModel.locationRadiusData.value ?: 3.0)
                else viewModel.getFireBaseMapXYData(Firebase.database)
            }
        }

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

        viewModel.locationRadiusData.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.onSaveLocationRadiusData(it)
                viewModel.getLocationNearStationList(it)
            }
        }
    }

    private fun getLastLocationServiceData() {
        try {
            lifecycleScope.launch(Dispatchers.IO) {
                val lastLocationTask = LocationServices.getFusedLocationProviderClient(requireContext()).lastLocation

                currentViewModel.setApplyCurrentLocation(lastLocationTask)
                    .catch { exception ->
                        Timber.e(exception)
                        showToast(R.string.toast_location_data_failed)
                        viewModel.setNowLocation(37.5283169,126.9294254)
                    }
                    .collectLatest { viewModel.setNowLocation(it.first , it.second) }
            }
        }catch (e : SecurityException) {
            Timber.e(e)
            showToast(R.string.toast_location_data_failed)
        }
    }
}