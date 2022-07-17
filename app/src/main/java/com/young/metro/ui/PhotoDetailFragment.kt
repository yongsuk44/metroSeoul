package com.young.metro.ui

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.base.BaseFragment
import com.young.metro.databinding.FragmentPhotoDetailBinding
import com.young.presentation.viewmodel.PhotoListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDetailFragment : BaseFragment<FragmentPhotoDetailBinding>() {
    override val layoutResource: Int = R.layout.fragment_photo_detail
    override val bindingVariable: Int = BR.vm

    val viewModel: PhotoListViewModel by viewModels()
    val args : PhotoDetailFragmentArgs by navArgs()

    override fun initBinding() {
        requireActivity()
        viewDataBinding.url = args.photoUrl
    }

    override fun observerLiveData() {

    }
}
