package com.young.metro.ui

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
import androidx.recyclerview.widget.RecyclerView
import com.young.metro.R
import com.young.metro.adapter.DropBoxAdapter
import com.young.metro.base.BindActivity
import com.young.metro.databinding.ActivityMainBinding
import com.young.metro.locationPermissionList
import com.young.presentation.viewmodel.AllRouteInformationViewModel
import com.young.presentation.viewmodel.MainViewModel
import com.young.presentation.viewmodel.PermissionViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BindActivity() {

    private val binding : ActivityMainBinding by binding(R.layout.activity_main)
    val viewModel by viewModels<MainViewModel>()
    val fullInformationViewModel by viewModels<AllRouteInformationViewModel>()
    val permissionViewModel by viewModels<PermissionViewModel>()

    var popupWindow: PopupWindow? = null

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

//        viewModel.loadSubWayFacilitiesData(getString(R.string.key))
        fullInformationViewModel.loadSubWayTelData(getString(R.string.seoulKey))
//        fullInformationViewModel.loadTrailTimeTableData(getString(R.string.trailKey) , railCode = railOprIsttCd , dayCd = dayCd , lineCode = lnCd , stationCode =  stinCd)
    }

    private fun observe() {
        viewModel.subWayFacilitiesData.observe(this) {
            with(it) {
                this
            }
        }
    }

    fun setPopUpWindow(
        parentView: View?,
        list: List<String>
    ): PopupWindow? {
        return parentView?.let {
            PopupWindow(parentView.width, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.popup_background, null))

                isOutsideTouchable = true
                isFocusable = true
                isTouchable = true

                LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.base_spinner_list, null, false)
                    .apply {
                        this.findViewById<RecyclerView>(R.id.rv_list).adapter =
                            DropBoxAdapter(viewModel).apply {
                                submitList(list)
                            }
                    }
                    .run { contentView = this }

                elevation = resources.getDimension(R.dimen.defaultViewShadow)
                translationMatrix(0f, resources.getDimension(R.dimen.defaultViewShadowTansY))

            }.also {
                popupWindow = it
            }
        } ?: kotlin.run {
            null
        }
    }

    fun Context.hideSoftKeyboard(view: View) {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}