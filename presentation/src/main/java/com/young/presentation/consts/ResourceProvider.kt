package com.young.presentation.consts

import android.app.Application
import javax.inject.Inject

class ResourceProvider @Inject constructor(
    private val context: Application
) {
    fun getString(resourceId:Int):String{
        return context.getString(resourceId)
    }
}