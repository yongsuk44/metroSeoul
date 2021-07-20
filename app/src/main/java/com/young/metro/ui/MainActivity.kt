package com.young.metro.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.translationMatrix
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.young.metro.R
import com.young.metro.adapter.DropBoxAdapter
import com.young.metro.base.BindActivity
import com.young.metro.databinding.ActivityMainBinding
import com.young.metro.locationPermissionList
import com.young.presentation.consts.EventObserver
import com.young.presentation.viewmodel.FullRouteInformationViewModel
import com.young.presentation.viewmodel.MainViewModel
import com.young.presentation.viewmodel.PermissionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : BindActivity() {

    private val binding: ActivityMainBinding by binding(R.layout.activity_main)
    val viewModel by viewModels<MainViewModel>()
    val fullInformationViewModel by viewModels<FullRouteInformationViewModel>()
    val permissionViewModel by viewModels<PermissionViewModel>()

    lateinit var popup : PopupWindow
    val dropBoxAdapter: DropBoxAdapter by lazy { DropBoxAdapter(viewModel) }

    val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        permissionViewModel.setLocationPermissionCheck(
            it.all { it.value == true }
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = viewModel
            infoViewModel = fullInformationViewModel
            key = getString(R.string.key)

            requestPermission.launch(locationPermissionList)
        }

        init()
        observe()
    }

    private fun init() {

        val railOprIsttCd = "S1"
        val dayCd = "8"
        val lnCd = "1"
        val stinCd = "133"

        viewModel.loadSubWayFacilitiesData(getString(R.string.key))
//        fullInformationViewModel.loadSubWayTelData(getString(R.string.seoulKey))
//        fullInformationViewModel.loadTrailTimeTableData(getString(R.string.trailKey) , railCode = railOprIsttCd , dayCd = dayCd , lineCode = lnCd , stationCode =  stinCd)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        popup = setPopUpWindow(binding.etStationSearch)
    }

    private fun observe() {

        viewModel.subWayFacilitiesData.observe(this) {

        }

        viewModel.stationList.observe(this) {
            dropBoxAdapter.apply {
                submitList(it)
            }
        }

        fullInformationViewModel.userSearchStationName.observe(this) {
            lifecycleScope.launch(Dispatchers.Main) {
                if (it.isNotEmpty()) {
                    searchDataFilter(it)
                    popup.showAsDropDown(binding.etStationSearch , 0 , 0)
                } else {
                    popup.dismiss()
                }
            }
        }
    }

    @SuppressLint("DefaultLocale")
    suspend fun searchDataFilter(searchData: String) {
        with(viewModel.stationList.value) {
            this?.filter {
                it.toLowerCase().contains(searchData.toLowerCase())
            }.run {
                dropBoxAdapter.submitList(this?.distinct())
//                dropBoxAdapter.notifyDataSetChanged()
            }
        }
    }

    fun setPopUpWindow(
        parentView: View
    ): PopupWindow {
        return PopupWindow(parentView.measuredWidth, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                setBackgroundDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.popup_background,
                        null
                    )
                )

                isOutsideTouchable = true

                LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.base_spinner_list, null, false)
                    .apply {
                        this.findViewById<RecyclerView>(R.id.rv_list).adapter = dropBoxAdapter
                    }
                    .run { contentView = this }

                elevation = resources.getDimension(R.dimen.defaultViewShadow)
                translationMatrix(0f, resources.getDimension(R.dimen.defaultViewShadowTansY))
            }

    }

    fun Context.hideSoftKeyboard(view: View) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}