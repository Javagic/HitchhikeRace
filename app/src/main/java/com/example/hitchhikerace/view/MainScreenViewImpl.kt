package com.example.hitchhikerace.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.database.navController
import com.example.hitchhikerace.database.navCreationFragment
import com.example.hitchhikerace.view.eventcreation.CarStartViewImpl.Companion.PERMISSION_COARSE_LOCATION
import com.example.hitchhikerace.view.eventcreation.CarStartViewImpl.Companion.PERMISSION_FINE_LOCATION
import kotlinx.android.synthetic.main.screen_main.*
import pub.devrel.easypermissions.EasyPermissions

class MainScreenViewImpl : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnCarStart.setOnClickListener {
            navController()?.navCreationFragment(bundleOf("event_type" to RaceEventType.CAR_START))
        }
        btnCrewManagment.setOnClickListener {
            view.findNavController()
                .navigate(
                    R.id.main_to_crew_management
                )
        }
        btnCurrentStatistic.setOnClickListener {
            view.findNavController().navigate(R.id.main_to_current_statistic)
        }
        btnRaceStart.setOnClickListener {
            navController()?.navCreationFragment(bundleOf("event_type" to RaceEventType.RACE_START))
        }
        tvCurrentRest.text = restString()
        requestLocationPermission(LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun restString() = PreferenceManager().getCurrentRest().run {
        getString(R.string.rest_pattern, hour, minute, partitions)
    }

    private fun requestLocationPermission(requestCode: Int) = requestPermissions(
        listOf(PERMISSION_COARSE_LOCATION, PERMISSION_FINE_LOCATION),
        R.string.need_permission,
        requestCode
    )

    private fun requestPermissions(
        permissions: List<String>,
        @StringRes rationale: Int,
        requestCode: Int
    ) = context?.run {
        if (!hasPermissions(permissions)) {
            EasyPermissions.requestPermissions(
                this@MainScreenViewImpl,
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

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

}