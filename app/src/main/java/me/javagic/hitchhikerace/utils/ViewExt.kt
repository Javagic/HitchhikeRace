package me.javagic.hitchhikerace.utils

import android.view.View
import android.widget.Button
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.navigation.Navigation

fun View.navigate(@IdRes id: Int) {
    Navigation.findNavController(this).navigate(id)
}

fun View.navigateWithTitle(@IdRes id: Int, @StringRes title: Int) {
    Navigation.findNavController(this)
        .navigate(id, bundleOf("screenTitle" to context.getString(title)))
}

fun Button.navigateOnClick(@IdRes id: Int) {
    setOnClickListener {
        navigate(id)
    }
}

var View.isVisible: Boolean
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }
    get() {
        return visibility == View.VISIBLE
    }