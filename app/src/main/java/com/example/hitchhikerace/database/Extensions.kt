package com.example.hitchhikerace.database

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController

fun Fragment.navController() = view?.findNavController()