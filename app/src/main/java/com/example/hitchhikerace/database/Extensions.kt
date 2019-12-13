package com.example.hitchhikerace.database

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.hitchhikerace.R

fun Fragment.navController() = view?.findNavController()

fun NavController.navCreationFragment(args: Bundle?) = navigate(R.id.main_to_create_event, args)