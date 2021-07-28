package com.young.metro.ui

import android.location.Geocoder
import androidx.fragment.app.viewModels
import com.google.android.gms.location.LocationServices
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.base.BaseFragment
import com.young.metro.base.showToast
import com.young.metro.databinding.FragmentLocationListBinding
import com.young.presentation.viewmodel.LocationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LocationListFragment : BaseFragment<FragmentLocationListBinding, LocationViewModel>() {
    override val layoutResource: Int = R.layout.fragment_location_list
    override val viewModel: LocationViewModel by viewModels()
    override val bindingVariable: Int = BR.vm

    override fun initBinding() {
        viewModel.loadAddressData()

        Geocoder(requireContext()).getFromLocationName("경기도 동두천시 평화로 2925", 1).run {
            showToast("좌표 ${this[0].latitude} , ${this[0].longitude}")
        }
    }

    override fun observerLiveData() {
        viewModel.locationStringList.observe(viewLifecycleOwner) {
            it
        }
    }

    override fun conversionFragment() {
    }

    fun getLastLocationData() {
        try {
            LocationServices.getFusedLocationProviderClient(requireContext()).run {
                lastLocation.addOnSuccessListener { location ->
                    location.latitude
                    location.longitude
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