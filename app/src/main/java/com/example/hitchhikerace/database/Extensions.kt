package com.example.hitchhikerace.database

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.hitchhikerace.R

fun Fragment.navController() = view?.findNavController()

fun NavController.navCreationFragment(args: Bundle?) = navigate(R.id.main_to_create_event, args)

var View.isVisible: Boolean
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }
    get() {
        return visibility == View.VISIBLE
    }