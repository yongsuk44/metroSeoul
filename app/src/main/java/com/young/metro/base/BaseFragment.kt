package com.young.metro.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

abstract class BaseFragment<B : ViewDataBinding, V : ViewModel> : Fragment() {

    abstract val layoutResource: Int
    lateinit var viewDataBinding: B
    abstract val bindingVariable: Int

    @LayoutRes var dialogLoading: Int? = null
    var progressDialog: AppCompatDialog? = null

    abstract fun initBinding()
    abstract fun observerLiveData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return performDataBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewDataBinding.lifecycleOwner = this@BaseFragment
        initBinding()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        observerLiveData()
    }


    private fun performDataBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutResource, container, false)
        return viewDataBinding.root
    }

    fun progressOn(fragment: Fragment) {
        progressDialog = AppCompatDialog(requireContext())

        when {
            fragment.isDetached -> return
            progressDialog?.isShowing ?: return -> return
            else -> {
                progressDialog?.apply {
                    setCancelable(true)
                    window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    setContentView(dialogLoading ?: return)
                }?.show()
            }
        }
    }

    fun progressOff() {
        progressDialog?.let {
            if (it.isShowing) it.dismiss()
        }
    }
}

fun Fragment.showToast(msg: CharSequence) {
    (activity as? BindActivity)?.showToast(msg)
}

fun Fragment.showToast(@StringRes msg : Int) {
    (activity as? BindActivity)?.showToast(getString(msg))
}
