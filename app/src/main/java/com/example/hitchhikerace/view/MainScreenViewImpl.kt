package com.example.hitchhikerace.view

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.*
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
                btnRaceStart.initToggleButton(
                    RaceEventType.RACE_START,
                    RaceEventType.RACE_FINISH,
                    getString(R.string.race_start_title),
                    getString(R.string.race_finish_title),
                    it,
                    R.drawable.background_button_event_active,
                    R.drawable.background_button_event_green
                )
                btnCarStart.initToggleButton(
                    RaceEventType.CAR_START,
                    RaceEventType.CAR_FINISH,
                    getString(R.string.car_start_title),
                    getString(R.string.car_finish_title),
                    it
                )
                btnRest.initToggleButton(
                    RaceEventType.REST_START,
                    RaceEventType.REST_FINISH,
                    getString(R.string.rest_start),
                    getString(R.string.rest_finish),
                    it
                )
                btnOrientation.initToggleButton(
                    RaceEventType.ORIENTATION_START,
                    RaceEventType.ORIENTATION_FINISH,
                    getString(R.string.orientation_start),
                    getString(R.string.orientation_finish),
                    it
                )
                btnRun.initToggleButton(
                    RaceEventType.RUN_START,
                    RaceEventType.RUN_FINISH,
                    getString(R.string.run_start),
                    getString(R.string.run_finish),
                    it
                )

            }
            .addTo(disposable)

        btnMeeting.setOnClickListener {
            navController()?.navCreationFragment(bundleOf("event_type" to RaceEventType.CREW_MEATING))
        }
        btnTakeCheckpoint.setOnClickListener {
            navController()?.navCreationFragment(bundleOf("event_type" to RaceEventType.TAKE_CHECKPOINT))
        }
        btnOther.setOnClickListener {
            navController()?.navCreationFragment(bundleOf("event_type" to RaceEventType.CUSTOM))
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
        btnLegend.setOnClickListener {
            view.findNavController().navigate(R.id.main_to_legend)
        }
        tvCurrentRest.text = restString()
        requestLocationPermission(LOCATION_PERMISSION_REQUEST_CODE)
        //TODO посчитать километраж гонки и пешком
    }

    private fun Button.initToggleButton(
        startEvent: RaceEventType,
        finishEvent: RaceEventType,
        startTitle: String,
        finishTitle: String,
        eventList: List<RaceEventEntity>,
        startDrawable: Int = R.drawable.background_button_default,
        finishDrawable: Int = R.drawable.background_button_event_active
    ): Boolean {
        if (eventList.filter { it.raceEventType == startEvent || it.raceEventType == finishEvent }.size % 2 == 0) {
            background = ContextCompat.getDrawable(
                context,
                startDrawable
            )
            text = startTitle
            setOnClickListener {
                navController()?.navCreationFragment(bundleOf("event_type" to startEvent))
            }
            return true
        } else {
            background = ContextCompat.getDrawable(
                context,
                finishDrawable
            )
            text = finishTitle
            setOnClickListener {
                navController()?.navCreationFragment(bundleOf("event_type" to finishEvent))
            }
            return false
        }
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
        const val PERMISSION_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        const val PERMISSION_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }
}