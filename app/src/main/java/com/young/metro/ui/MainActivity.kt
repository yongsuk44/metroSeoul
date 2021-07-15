package com.young.metro.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.young.metro.R
import com.young.metro.base.BindActivity
import com.young.metro.databinding.ActivityMainBinding
import com.young.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : BindActivity() {

    private val binding : ActivityMainBinding by binding(R.layout.activity_main)
    val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.apply {
            lifecycleOwner = this@MainActivity
            vm = viewModel
            key = getString(R.string.key)
        }

        init()
    }

    private fun init() {
        viewModel.subWayFacilitiesData.observe(this) {
            with(it) {
                this
            }
        }
    }
}