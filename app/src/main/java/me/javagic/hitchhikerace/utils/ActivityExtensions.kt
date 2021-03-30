package me.javagic.hitchhikerace.utils

import android.app.Activity
import android.content.Context
import android.view.Gravity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.StringRes
import me.javagic.hitchhikerace.R
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

fun Activity.showToast() {
    val layout = layoutInflater.inflate(R.layout.toast_confirmation, null)
    val toast = Toast(applicationContext)
    toast.setGravity(Gravity.BOTTOM, 0, 0)
    toast.duration = Toast.LENGTH_SHORT
    toast.view = layout
    toast.show()
}

fun Activity?.hideKeyboard(): Boolean {
    this ?: return false
    try {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocus = currentFocus
        if (currentFocus != null) {
            val windowToken = currentFocus.windowToken
            if (windowToken != null) {
                return inputManager.hideSoftInputFromWindow(windowToken, 0)
            }
        }
    } catch (e: Exception) {
        Timber.e(e)
    }
    return false
}

fun Activity.requestUserPermissions(
    permissions: List<String>,
    @StringRes rationale: Int,
    requestCode: Int
) {
    if (!hasPermissions(permissions)) {
        EasyPermissions.requestPermissions(
            this,
            this.getString(rationale),
            requestCode,
            *permissions.toTypedArray()
        )
    }
}