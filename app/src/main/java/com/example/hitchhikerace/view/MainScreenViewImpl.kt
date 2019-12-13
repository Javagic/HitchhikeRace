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
import com.example.hitchhikerace.database.DataBaseInteractorImpl
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.database.navController
import com.example.hitchhikerace.database.navCreationFragment
import com.example.hitchhikerace.view.eventcreation.CarStartViewImpl.Companion.PERMISSION_COARSE_LOCATION
import com.example.hitchhikerace.view.eventcreation.CarStartViewImpl.Companion.PERMISSION_FINE_LOCATION
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.screen_main.*
import pub.devrel.easypermissions.EasyPermissions

class MainScreenViewImpl : Fragment() {

    private val disposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DataBaseInteractorImpl().getAllRaceEventList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                btnRaceStart.setOnClickListener {
                    navController()?.navCreationFragment(bundleOf("event_type" to RaceEventType.RACE_START))
                }
                if (it.filter { it.raceEventType == RaceEventType.CAR_FINISH || it.raceEventType == RaceEventType.CAR_START }.size % 2 == 0) {
                    btnCarStart.text = getString(R.string.car_start_title)
                    btnCarStart.setOnClickListener {
                        navController()?.navCreationFragment(bundleOf("event_type" to RaceEventType.CAR_START))
                    }
                } else {
                    btnCarStart.text = getString(R.string.car_finish_title)
                    btnCarStart.setOnClickListener {
                        navController()?.navCreationFragment(bundleOf("event_type" to RaceEventType.CAR_FINISH))
                    }
                }
            }
            .addTo(disposable)
        btnCrewManagment.setOnClickListener {
            view.findNavController()
                .navigate(
                    R.id.main_to_crew_management
                )
        }
        btnCurrentStatistic.setOnClickListener {
            view.findNavController().navigate(R.id.main_to_current_statistic)
        }
        tvCurrentRest.text = restString()
        requestLocationPermission(LOCATION_PERMISSION_REQUEST_CODE)
        //TODO посчитать километраж гонки и пешком
        //TODO очистка всех данных????
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

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }
}