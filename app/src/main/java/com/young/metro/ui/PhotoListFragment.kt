package com.young.metro.ui

import androidx.fragment.app.viewModels
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.base.BaseFragment
import com.young.metro.databinding.FragmentPhotoListBinding
import com.young.presentation.viewmodel.PhotoListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoListFragment : BaseFragment<FragmentPhotoListBinding , PhotoListViewModel>() {
    override val layoutResource: Int = R.layout.fragment_photo_list
    override val viewModel: PhotoListViewModel by viewModels()
    override val bindingVariable: Int = BR.vm

    override fun initBinding() {

    }

    override fun observerLiveData() {

    }
}