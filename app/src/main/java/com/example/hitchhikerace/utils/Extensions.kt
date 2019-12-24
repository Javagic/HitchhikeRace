package com.example.hitchhikerace.utils

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

fun Fragment.navController() = view?.findNavController()

