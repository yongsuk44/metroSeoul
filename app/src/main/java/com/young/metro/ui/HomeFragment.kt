package com.young.metro.ui

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.young.base.locationPermissionList
import com.young.metro.BR
import com.young.metro.BuildConfig
import com.young.metro.R
import com.young.metro.adapter.DropBoxAdapter
import com.young.metro.base.BaseFragment
import com.young.metro.base.showToast
import com.young.metro.databinding.FragmentHomeBinding
import com.young.presentation.consts.EventObserver
import com.young.presentation.viewmodel.FullRouteInformationViewModel
import com.young.presentation.viewmodel.LocationViewModel
import com.young.presentation.viewmodel.PermissionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@ExperimentalCoroutinesApi
@FlowPreview
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val layoutResource: Int = R.layout.fragment_home
    override val bindingVariable: Int = BR.vm

    private val fullRouteInformationViewModel by viewModels<FullRouteInformationViewModel>()
    private val permissionViewModel by viewModels<PermissionViewModel>()
    private val locationViewModel by viewModels<LocationViewModel>()

    private val dropBoxAdapter: DropBoxAdapter by lazy { DropBoxAdapter(fullRouteInformationViewModel) }

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionCheck ->
        permissionViewModel.setLocationPermissionCheck(
            permissionCheck.all { it.value == true }
        )
    }

    override fun initBinding() {
        viewDataBinding.permissionVm = permissionViewModel
        viewDataBinding.trailKey = BuildConfig.trailKey

        requestPermission.launch(locationPermissionList)
        locationViewModel.updateLocationService()

        fullRouteInformationViewModel.insertAllStationCodes(BuildConfig.seoulKey)
        fullRouteInformationViewModel.loadFullRouteInformation(BuildConfig.trailKey)

        viewDataBinding.rvPopupList.adapter = dropBoxAdapter
    }


    override fun observerLiveData() {
        fullRouteInformationViewModel.filterList.observe(viewLifecycleOwner) {
            dropBoxAdapter.submitList(it)
            fullRouteInformationViewModel.onSearchEditViewClick(!it.isNullOrEmpty())
        }

        fullRouteInformationViewModel.searchEditViewClick.observe(viewLifecycleOwner, EventObserver {
            fullRouteInformationViewModel.onPopupWindowViewVisibleCheck(it)
        })

        permissionViewModel.locationSearchClick.observe(viewLifecycleOwner, EventObserver {
            if (it) {
                hideSoftKeyboard(this.requireView())
                findNavController().navigate(R.id.action_fragment_home_to_locationListFragment)
            } else {
                requestPermission.launch(locationPermissionList)
            }
        })

        permissionViewModel.locationPermission.observe(viewLifecycleOwner) {
            if (!it) showToast(R.string.toast_location_permission_failed)
        }

        fullRouteInformationViewModel.searchActionStation.observe(viewLifecycleOwner, EventObserver {
            hideSoftKeyboard(requireView())
            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToStationInformationDetailFragment(
                    it.stinCd.toTypedArray(),
                    it.stinNm
                )
            )
        })

        fullRouteInformationViewModel.failedInformationData.observe(viewLifecycleOwner) {
            if (it) showToast(R.string.text_data_not_found)
        }
    }

    override fun onResume() {
        hideSoftKeyboard(requireView())
        super.onResume()
    }

    private fun hideSoftKeyboard(view: View) {
        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}