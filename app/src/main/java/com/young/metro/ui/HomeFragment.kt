package com.young.metro.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.translationMatrix
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.adapter.DropBoxAdapter
import com.young.metro.base.BaseFragment
import com.young.metro.base.showToast
import com.young.metro.databinding.BaseSpinnerListBinding
import com.young.metro.databinding.FragmentHomeBinding
import com.young.metro.locationPermissionList
import com.young.metro.util.toTransitionGroup
import com.young.presentation.consts.EventObserver
import com.young.presentation.viewmodel.FullRouteInformationViewModel
import com.young.presentation.viewmodel.PermissionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import timber.log.Timber

@FlowPreview
@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, FullRouteInformationViewModel>() {
    override val layoutResource: Int = R.layout.fragment_home
    override val viewModel: FullRouteInformationViewModel by viewModels()
    override val bindingVariable: Int = BR.vm

    private val permissionViewModel by viewModels<PermissionViewModel>()

    lateinit var popup: PopupWindow
    private val dropBoxAdapter: DropBoxAdapter by lazy { DropBoxAdapter(viewModel) }

    private val popUpListBinding by lazy {
        DataBindingUtil.bind<BaseSpinnerListBinding>(
            LayoutInflater.from(requireContext())
                .inflate(R.layout.base_spinner_list, null, false)
        )
    }

    private val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }

    private val locationRequest by lazy {
        LocationRequest.create().apply {
            interval = 5 * 1000L
            fastestInterval = 1 * 1000L
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionCheck ->
        permissionViewModel.setLocationPermissionCheck(
            permissionCheck.all { it.value == true }
        )
    }

    override fun initBinding() {
        viewDataBinding.permissionVm = permissionViewModel
        requestPermission.launch(locationPermissionList)
        getRequestLocationData()
        viewModel.loadFullRouteInformation()
    }


    override fun observerLiveData() {

        viewDataBinding.etStationSearch.viewTreeObserver.addOnWindowFocusChangeListener {
            popup = setPopUpWindow(viewDataBinding.etStationSearch)
        }

        viewModel.userSearchStationName.observe(viewLifecycleOwner) { searchString ->
            lifecycleScope.launch(Dispatchers.Main) {
                searchString.isNotEmpty().also {
                    if (it) {
                        searchDataFilter(searchString)
                    }

                    viewModel.onSearchEditViewClick(it)
                }
            }
        }

        viewModel.searchEditViewClick.observe(viewLifecycleOwner, EventObserver {
            if (it)
                popup.showAsDropDown(viewDataBinding.etStationSearch, 0, 0)
            else
                popup.dismiss()
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

        viewModel.searchActionStation.observe(viewLifecycleOwner, EventObserver {
            hideSoftKeyboard(requireView())
            popup.dismiss()

            findNavController().navigate(
                HomeFragmentDirections.actionFragmentHomeToStationInformationDetailFragment(it.stinCd.toTypedArray() , it.stinNm)
            )
        })
    }

    @SuppressLint("DefaultLocale")
    private fun searchDataFilter(searchData: String) {
        viewModel.fullRouteInformation.value?.filter {
            it.stinNm.toLowerCase().contains(searchData.toLowerCase())
        }.run {
            dropBoxAdapter.submitList(this)
        }
    }

    private fun setPopUpWindow(
        parentView: View
    ): PopupWindow {
        return PopupWindow(parentView.measuredWidth, ViewGroup.LayoutParams.WRAP_CONTENT).apply {

            setBackgroundDrawable(
                ResourcesCompat.getDrawable(resources, R.drawable.popup_background, null))

                popUpListBinding.apply {
                    this?.vm = viewModel
                    this?.rvList?.apply {
                        adapter = dropBoxAdapter
                    }
                }.run {
                    contentView = this?.root
                }

            isOutsideTouchable = true
            elevation = resources.getDimension(R.dimen.defaultViewShadow)
            translationMatrix(0f, resources.getDimension(R.dimen.defaultViewShadowTansY))
        }

    }

    private fun hideSoftKeyboard(view: View) {
        (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun getRequestLocationData() {
        try {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                requestCallback,
                Looper.getMainLooper()
            )
        } catch (e: SecurityException) {
            Timber.e("Location Data Update Failed : $e")
        }
    }

    override fun onResume() {
        hideSoftKeyboard(requireView())
        super.onResume()
    }

    private val requestCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            Timber.d("Update Latitude : ${p0.lastLocation.latitude} \nUpdate Longitude : ${p0.lastLocation.longitude}")
            fusedLocationProviderClient.removeLocationUpdates(this)
        }
    }

    override fun onDestroy() {
        popup.dismiss()
        super.onDestroy()
    }
}