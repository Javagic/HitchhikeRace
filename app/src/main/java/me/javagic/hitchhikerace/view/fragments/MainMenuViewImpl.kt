package me.javagic.hitchhikerace.view.fragments

import android.Manifest
import android.os.Bundle
import android.view.View
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.screen_main_menu.*
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.utils.navigate
import me.javagic.hitchhikerace.utils.navigateWithTitle
import me.javagic.hitchhikerace.utils.requestUserPermissions
import me.javagic.hitchhikerace.view.fragments.base.BaseFragment

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
            listOf(
                PERMISSION_COARSE_LOCATION,
                PERMISSION_FINE_LOCATION,
                PERMISSION_WRITE_EXTERNAL_STORAGE,
                PERMISSION_READ_EXTERNAL_STORAGE
            ),
            R.string.need_permission,
            requestCode
        )
    }

    companion object {
        const val PERMISSION_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        const val PERMISSION_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val PERMISSION_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
        const val PERMISSION_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }

}