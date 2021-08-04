package com.young.metro.ui

import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.young.domain.model.DomainStationNameAndMapXY
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.adapter.LineLogoAdapter
import com.young.metro.adapter.LocationNearAdapter
import com.young.metro.base.BaseFragment
import com.young.metro.base.showToast
import com.young.metro.databinding.FragmentLocationListBinding
import com.young.presentation.consts.EventObserver
import com.young.presentation.model.UiStationNameAndMapXY
import com.young.presentation.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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
        viewDataBinding.rvLocationNearStation.adapter = adapter
    }

    override fun observerLiveData() {
        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) viewDataBinding.lottieLoading.cancelAnimation()
        }
        viewModel.stationCoordinateDataSize.observe(viewLifecycleOwner) {
            if (it > 0) viewModel.getLocationNearStationList()
            else getFireBaseStationNameAndMapXyData()
        }

        viewModel.stationNameAndMapXY.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.stationClick.observe(viewLifecycleOwner , EventObserver {

            findNavController().navigate(
                R.id.action_locationListFragment_to_stationInformationDetailFragment
            )
        })
    }

    private fun getFireBaseStationNameAndMapXyData() {
        lifecycleScope.launch {
            flowOf(Firebase.database.reference.child("StationLocationData").get())
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    Timber.e(e)
                    showToast(R.string.toast_location_data_failed)
                }
                .collect {
                    it.addOnSuccessListener {
                        viewModel.setAllStationNameAndMapXYData(
                            (it.value as List<String>).map {
                                Gson().fromJson(it, UiStationNameAndMapXY::class.java)
                            }
                        )
                    }.addOnCanceledListener {
                        showToast(R.string.toast_location_data_cancel)
                    }
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