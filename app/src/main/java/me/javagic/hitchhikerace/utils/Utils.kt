package me.javagic.hitchhikerace.utils

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

const val SHIFT = "\n"

fun EditText?.showKeyboard() {
    this ?: return
    val inputManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
    requestFocus()
}

inline fun <R> tryOrNull(block: () -> R): R? {
    return try {
        block()
    } catch (e: Exception) {
        Timber.e(e)
        return null
    }
}

fun Context.hasPermissions(permissions: List<String>): Boolean =
    EasyPermissions.hasPermissions(
        this,
        *permissions.toTypedArray()
    )
