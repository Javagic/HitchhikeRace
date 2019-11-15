package com.example.hitchhikerace

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.PermissionChecker

fun Context.hasPermission(permission: String): Boolean {
    val permissionState = PermissionChecker.checkSelfPermission(this, permission)
    return PackageManager.PERMISSION_GRANTED == permissionState
}
