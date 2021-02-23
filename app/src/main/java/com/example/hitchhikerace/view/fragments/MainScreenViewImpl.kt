package com.example.hitchhikerace.view.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.example.hitchhikerace.R
import com.example.hitchhikerace.app.RaceApplication
import com.example.hitchhikerace.data.PreferenceManager
import com.example.hitchhikerace.data.database.entity.RaceEventEntity
import com.example.hitchhikerace.data.pojo.RaceEventType
import com.example.hitchhikerace.domain.RaceEventInteractor
import com.example.hitchhikerace.utils.navigateOnClick
import com.example.hitchhikerace.view.fragments.base.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.screen_events_main.*
import javax.inject.Inject

//TODO rename
class MainScreenViewImpl : BaseFragment() {

    @Inject
    lateinit var eventInteractor: RaceEventInteractor

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private val disposable = CompositeDisposable()

    override fun getLayoutId(): Int = R.layout.screen_events_main

    override fun close() {
        activity?.finish()
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        RaceApplication.appComponent.inject(this)
        eventInteractor.getAllRaceEventList(PreferenceManager().getCurrentRace())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { eventList ->
                btnRaceStart.initToggleButton(
                    RaceEventType.RACE_START,
                    RaceEventType.RACE_FINISH,
                    getString(R.string.race_start_title),
                    getString(R.string.race_finish_title),
                    eventList,
                    R.drawable.background_button_event_active,
                    R.drawable.background_button_event_green
                )
                if (eventList.isEmpty()) {
                    showRaceNotStartedUi()
                } else {
                    showRaceIsStartedUi(eventList)
                }
            }
            .addTo(disposable)
        btnSettings.navigateOnClick(R.id.main_to_settings)
        tvCurrentRest.text = restString()
        //TODO посчитать километраж гонки и пешком
    }

    private fun showRaceIsStartedUi(eventList: List<RaceEventEntity>) {
        btnCarStart.initToggleButton(
            RaceEventType.CAR_START,
            RaceEventType.CAR_FINISH,
            getString(R.string.car_start_title),
            getString(R.string.car_finish_title),
            eventList
        )
        btnRest.initToggleButton(
            RaceEventType.REST_START,
            RaceEventType.REST_FINISH,
            getString(R.string.rest_start),
            getString(R.string.rest_finish),
            eventList
        )
        btnOrientation.initToggleButton(
            RaceEventType.ORIENTATION_START,
            RaceEventType.ORIENTATION_FINISH,
            getString(R.string.orientation_start),
            getString(R.string.orientation_finish),
            eventList
        )
        btnRun.initToggleButton(
            RaceEventType.RUN_START,
            RaceEventType.RUN_FINISH,
            getString(R.string.run_start),
            getString(R.string.run_finish),
            eventList
        )
        btnMeeting.navigateOnClickToCreation(RaceEventType.CREW_MEETING)
        btnTakeCheckpoint.navigateOnClickToCreation(RaceEventType.TAKE_CHECKPOINT)
        btnOther.navigateOnClickToCreation(RaceEventType.CUSTOM)
    }

    private fun showRaceNotStartedUi() {
        val disabledDrawable = ContextCompat.getDrawable(
            btnCarStart.context,
            R.drawable.background_button_disabled
        )
        btnCarStart.background = disabledDrawable
        btnRest.background = disabledDrawable
        btnOrientation.background = disabledDrawable
        btnRun.background = disabledDrawable
        btnMeeting.background = disabledDrawable
        btnTakeCheckpoint.background = disabledDrawable
        btnOther.background = disabledDrawable
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
            navigateOnClickToCreation(startEvent)
            return true
        } else {
            background = ContextCompat.getDrawable(
                context,
                finishDrawable
            )
            text = finishTitle
            navigateOnClickToCreation(finishEvent)
            return false
        }
    }

    private fun restString() = PreferenceManager().getCurrentRest().run {
        getString(R.string.rest_pattern, hour, minute, partitions)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.clear()
    }

    private fun Button.navigateOnClickToCreation(type: RaceEventType) {
        setOnClickListener {
            findNavController().navigate(R.id.main_to_create_event, bundleOf("event_type" to type))
        }
    }

}