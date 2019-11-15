package com.example.hitchhikerace.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.DataBaseInteractorImpl
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.database.hideKeyboard
import com.example.hitchhikerace.database.navController
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.screen_base_event.*

class CreateBaseEventViewImpl : Fragment() {

    private val eventType by lazy {
        arguments?.get("event_type") as RaceEventType
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.screen_base_event, container, false)

    //TODO хорошее место для рефакторинга
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val childFragment = EventTypeMapper().getFeatureRaceEventFragment(view.context, eventType)
        val transaction = childFragmentManager.beginTransaction()
        transaction.replace(R.id.eventFeatureContainer, childFragment).commit()
        tvEventTitle.text = getString(childFragment.getEventTitle())
        btnCreateEvent.setOnClickListener {
            val newEvent = childFragment.createRaceEventViewModel()
            if (eventType == RaceEventType.REST_FINISH) {
                refreshRest(newEvent.hour.toInt(), newEvent.minute.toInt())
                    .subscribe {
                        DataBaseInteractorImpl().addEvent(newEvent)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::close) {}
                    }
            } else {
                DataBaseInteractorImpl().addEvent(newEvent)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::close) {}
            }
        }
        btnCancel.setOnClickListener {
            close()
        }
    }

    @SuppressLint("CheckResult")
    private fun refreshRest(newHour: Int, newMinute: Int): Completable {
        return DataBaseInteractorImpl().getAllRaceEventListSingle()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                val restStartEvent =
                    it.sortedBy { it.id }.findLast { it.raceEventType == RaceEventType.REST_START }!!
                val oldHour = restStartEvent.hour.toInt()
                val oldMinute = restStartEvent.minute.toInt()
                val diffMinutes = if (newHour < oldHour) {
                    (newHour * 60 + newMinute) + ((23 - oldHour) * 60 + 60 - oldMinute)
                } else {
                    (newHour * 60 + newMinute) - (oldMinute + oldHour * 60)
                }
                val currentRest = PreferenceManager().getCurrentRest()
                val newRestMinute = currentRest.toMinutes() - diffMinutes
                PreferenceManager().saveCurrentRest(
                    RestEntity(
                        newRestMinute / 60,
                        newRestMinute - ((newRestMinute / 60) * 60),
                        currentRest.partitions - 1
                    )
                )
            }
            .ignoreElement()
    }

    private fun close() {
        activity.hideKeyboard()
        navController()?.popBackStack()
    }

}