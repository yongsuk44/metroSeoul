package com.young.metro.ui

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.young.metro.R
import com.young.metro.base.BindActivity
import com.young.metro.databinding.ActivityMainBinding
import com.young.metro.locationPermissionList
import com.young.presentation.viewmodel.MainViewModel
import com.young.presentation.viewmodel.PermissionViewModel
import com.young.presentation.viewmodel.SubWayTelViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BindActivity() {

    private val binding : ActivityMainBinding by binding(R.layout.activity_main)
    val viewModel by viewModels<MainViewModel>()
    val tel by viewModels<SubWayTelViewModel>()
    val permissionViewModel by viewModels<PermissionViewModel>()

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
        viewModel.loadSubWayFacilitiesData(getString(R.string.key))
        tel.loadSubWayTelData(getString(R.string.seoulKey))
    }

    private fun observe() {
        viewModel.subWayFacilitiesData.observe(this) {
            with(it) {
                this
            }
        }

    }
}