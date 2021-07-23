package com.young.metro.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.translationMatrix
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.young.metro.BR
import com.young.metro.R
import com.young.metro.adapter.DropBoxAdapter
import com.young.metro.base.BaseFragment
import com.young.metro.databinding.BaseSpinnerListBinding
import com.young.metro.databinding.FragmentHomeBinding
import com.young.metro.locationPermissionList
import com.young.presentation.consts.EventObserver
import com.young.presentation.viewmodel.FullRouteInformationViewModel
import com.young.presentation.viewmodel.PermissionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding , FullRouteInformationViewModel>() {
    override val layoutResource: Int = R.layout.fragment_home
    override val viewModel: FullRouteInformationViewModel by viewModels()
    override val bindingVariable: Int = BR.vm

    private val permissionViewModel by viewModels<PermissionViewModel>()

    lateinit var popup: PopupWindow
    private val dropBoxAdapter: DropBoxAdapter by lazy { DropBoxAdapter(viewModel) }

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionCheck ->
        permissionViewModel.setLocationPermissionCheck(
            permissionCheck.all { it.value == true }
        )
    }

    override fun initBinding() {
        requestPermission.launch(locationPermissionList)

        viewModel.loadFullRouteInformation()
    }

    override fun observerLiveData() {

        viewDataBinding.etStationSearch.viewTreeObserver.addOnWindowFocusChangeListener {
            popup = setPopUpWindow(viewDataBinding.etStationSearch)
        }

        viewModel.userSearchStationName.observe(this) { searchString ->
            lifecycleScope.launch(Dispatchers.Main) {
                searchString.isNotEmpty().also {
                    if (it) searchDataFilter(searchString)
                    viewModel.onSearchEditViewClick(it)
                }
            }
        }

        viewModel.searchEditViewClick.observe(this, EventObserver {
            if (it) popup.showAsDropDown(viewDataBinding.etStationSearch, 0, 0)
            else popup.dismiss()
        })

        viewModel.searchActionStation.observe(this, EventObserver {
        })
    }

    override fun conversionFragment() {

    }

    @SuppressLint("DefaultLocale")
    fun searchDataFilter(searchData: String) {
        viewModel.fullRouteInformation.value?.filter {
            it.stinNm.toLowerCase().contains(searchData.toLowerCase())
        }.run {
            dropBoxAdapter.submitList(this)
        }
    }

    override fun onResume() {
        hideSoftKeyboard(viewDataBinding.etStationSearch)
        super.onResume()
    }

    private fun setPopUpWindow(
        parentView: View
    ): PopupWindow {
        return PopupWindow(parentView.measuredWidth, ViewGroup.LayoutParams.WRAP_CONTENT).apply {

            setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.popup_background, null))

            DataBindingUtil.bind<BaseSpinnerListBinding>(
                LayoutInflater.from(requireContext())
                    .inflate(R.layout.base_spinner_list, null, false)
            )
                .apply {
                    this?.vm = viewModel
                    this?.rvList?.adapter = dropBoxAdapter
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
}