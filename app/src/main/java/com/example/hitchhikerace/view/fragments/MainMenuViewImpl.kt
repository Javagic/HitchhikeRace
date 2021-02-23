package com.example.hitchhikerace.view.fragments

import android.Manifest
import android.os.Bundle
import android.view.View
import com.example.hitchhikerace.R
import com.example.hitchhikerace.utils.navigate
import com.example.hitchhikerace.utils.navigateWithTitle
import com.example.hitchhikerace.utils.requestUserPermissions
import com.example.hitchhikerace.view.fragments.base.BaseFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.screen_main_menu.*

class MainMenuViewImpl : BaseFragment() {

    private val disposable = CompositeDisposable()

    override fun getLayoutId(): Int = R.layout.screen_main_menu

    override fun initView(view: View, savedInstanceState: Bundle?) {
        requestLocationPermission(LOCATION_PERMISSION_REQUEST_CODE)
        btnRaceStart.setOnClickListener {
            view.navigateWithTitle(R.id.main_menu_to_create_new_race, R.string.race_start_title)
        }
        btnRacesHistory.setOnClickListener {
            view.navigate(R.id.main_menu_to_races_history)
        }
    }

    private fun requestLocationPermission(requestCode: Int) {
        activity?.requestUserPermissions(
            listOf(PERMISSION_COARSE_LOCATION, PERMISSION_FINE_LOCATION),
            R.string.need_permission,
            requestCode
        )
    }

    companion object {
        const val PERMISSION_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        const val PERMISSION_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }

}