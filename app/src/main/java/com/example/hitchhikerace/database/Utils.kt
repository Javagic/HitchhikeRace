package com.example.hitchhikerace.database

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import timber.log.Timber

fun Context?.showKeyboard() {
    this ?: return
    val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
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