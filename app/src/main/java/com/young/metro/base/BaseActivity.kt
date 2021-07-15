package com.young.metro.base

import android.os.Build
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.young.metro.util.StatusColor
import com.young.metro.util.setStatusBarColor
import java.util.*

abstract class BindActivity : AppCompatActivity() {
    protected inline fun <reified T : ViewDataBinding> binding(@LayoutRes resID: Int) : Lazy<T>
            = lazy { DataBindingUtil.setContentView<T>(this, resID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(this , StatusColor.WHITE)
    }
}
