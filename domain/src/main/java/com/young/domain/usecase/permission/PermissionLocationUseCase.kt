package com.young.domain.usecase.permission

import android.content.Context
import android.content.pm.PackageManager
import com.young.base.locationPermissionList

internal typealias PermissionLocationBaseUseCase = () -> Boolean

class PermissionLocationUseCase constructor(
    private val context: Context
) : PermissionLocationBaseUseCase {
    override fun invoke(): Boolean = locationPermissionList.all { permission ->
        context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }
}