package com.young.metro.ui

import android.location.Geocoder
import android.widget.TextView
import androidx.fragment.app.viewModels
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
import com.young.presentation.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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

    override fun initBinding() {
        viewDataBinding.lottieLoading.playAnimation()

        getLastLocationServiceData()
        viewDataBinding.rvLocationNearStation.adapter = this@LocationListFragment.adapter
    }

    override fun observerLiveData() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) viewDataBinding.lottieLoading.cancelAnimation()
        }

        viewModel.stationCoordinateDataSize.observe(viewLifecycleOwner) {
            it?.let {
                if (it > 0) viewModel.onLocationRadiusData(viewModel.locationRadiusData.value ?: 3.0)
                else viewModel.getFireBaseMapXYData(Firebase.database)
            }
        }

        viewModel.stationNameAndMapXY.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            waitForTransition(viewDataBinding.rvLocationNearStation)
        }

        viewModel.stationClick.observe(viewLifecycleOwner , EventObserver {
            val holderBinding = viewDataBinding.rvLocationNearStation.findViewHolderForLayoutPosition(viewModel.selectPosition.value ?: 0)?.itemView ?: return@EventObserver
            val extras = FragmentNavigatorExtras(
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
            LocationServices.getFusedLocationProviderClient(requireContext()).run {
                lastLocation.addOnSuccessListener { location ->
                    if (location == null) {
                        throw SecurityException("Location Data를 얻지 못함")
                    } else {
                        Geocoder(requireContext(), Locale.KOREAN).getFromLocation(
                            location.latitude,
                            location.longitude,
                            2
                        )?.also { address ->
                            when (address.size) {
                                1 -> {
                                    viewModel.setNowLocation(address[0].latitude, address[0].longitude)
                                }

                                2 -> {
                                    viewModel.setNowLocation(
                                        listOf(address[0].latitude, address[1].latitude).average(),
                                        listOf(address[0].longitude, address[1].longitude).average()
                                    )
                                }

                                else -> showToast(R.string.toast_location_data_failed)
                            }
                        }
                    }
                }.addOnFailureListener { e ->
                    Timber.e(e)
                    showToast(R.string.toast_location_data_failed)
                }.addOnCanceledListener {
                    showToast(R.string.toast_location_data_cancel)
                }
            }
        } catch (e: SecurityException) {
            Timber.e(e)
            showToast(R.string.toast_location_data_failed)
        }
    }
}