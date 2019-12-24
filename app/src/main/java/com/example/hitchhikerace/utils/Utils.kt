package com.example.hitchhikerace.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.StringRes
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

const val SHIFT = "\n"

fun EditText?.showKeyboard() {
    this ?: return
    val inputManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    requestFocus()
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

inline fun <R> tryOrNull(block: () -> R): R? {
    return try {
        block()
    } catch (e: Exception) {
        Timber.e(e)
        return null
    }
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

private fun Context.hasPermissions(permissions: List<String>): Boolean =
    EasyPermissions.hasPermissions(
        this,
        *permissions.toTypedArray()
    )
