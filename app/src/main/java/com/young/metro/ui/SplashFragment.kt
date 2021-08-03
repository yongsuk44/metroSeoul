package com.young.metro.ui

import androidx.fragment.app.viewModels
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.base.BaseFragment
import com.young.metro.databinding.FragmentSplashBinding
import com.young.presentation.viewmodel.LocationViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class SplashFragment : BaseFragment<FragmentSplashBinding , LocationViewModel>() {
    override val layoutResource: Int
        get() = R.layout.fragment_splash
    override val viewModel: LocationViewModel by viewModels()
    override val bindingVariable: Int = BR.vm

    override fun initBinding() {

    }

    override fun observerLiveData() {

    }

    override fun conversionFragment() {

    }
}