package com.example.hitchhikerace.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.hitchhikerace.view.activity.MainActivity
import com.example.hitchhikerace.R
import com.example.hitchhikerace.app.RaceApplication
import com.example.hitchhikerace.data.database.RaceEventEntity
import com.example.hitchhikerace.domain.RaceEventInteractor
import com.example.hitchhikerace.data.database.RaceEventType
import com.example.hitchhikerace.utils.isVisible
import com.example.hitchhikerace.utils.subscribeWithErrorLogConsumer
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.screen_base_event.*
import javax.inject.Inject

class CreateBaseEventViewImpl : BaseFragment() {

    @Inject
    lateinit var eventInteractor: RaceEventInteractor

    @Inject
    lateinit var preferenceManager: PreferenceManager

    private val eventType by lazy {
        arguments?.get("event_type") as RaceEventType
    }

    private val raceEvent by lazy {
        arguments?.get("raceEvent") as? RaceEventEntity
    }

    override fun getLayoutId(): Int = R.layout.screen_base_event

    //TODO хорошее место для рефакторинга
    override fun initView(view: View, savedInstanceState: Bundle?) {
        RaceApplication.appComponent.inject(this)
        val childFragment =
            EventTypeMapper().getFeatureRaceEventFragment(view.context, eventType, raceEvent)
        val transaction = childFragmentManager.beginTransaction()
        //TODO commit exception exploit
        transaction.replace(R.id.eventFeatureContainer, childFragment).commit()
        tvEventTitle.text = getString(childFragment.getEventTitle())
        btnCreateEvent.setOnClickListener {
            childFragment.validateInput().takeIf { it.isNotEmpty() }?.let {
                tvWarning.isVisible = true
                tvWarning.text = it
                return@setOnClickListener
            }
            val newEvent = childFragment.createRaceEventViewModel()
            if (eventType == RaceEventType.REST_FINISH) {
                refreshRest(newEvent.hour.toInt(), newEvent.minute.toInt(), raceEvent?.id)
                    .subscribeOn(Schedulers.io())
                    .subscribe {
                        eventInteractor.addEvent(raceEvent?.id ?: 0, newEvent)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                close()
                                (activity as? MainActivity)?.showToast()
                            }, {})
                    }
            } else if (eventType == RaceEventType.REST_START && raceEvent != null) {

            } else {
                eventInteractor.addEvent(raceEvent?.id ?: 0, newEvent)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWithErrorLogConsumer {
                        if (eventType == RaceEventType.RACE_FINISH) {
                            preferenceManager.saveCurrentRace(RACE_EMPTY_ID)
                            preferenceManager.saveCurrentRest(RestEntity())
                            findNavController().setGraph(R.navigation.main_menu_navigation)
                            (activity as? MainActivity)?.showToast()
                        } else {
                            close()
                            (activity as? MainActivity)?.showToast()
                        }
                    }
            }
        }
        btnCancel.setOnClickListener {
            close()
        }
    }

    @SuppressLint("CheckResult")
    private fun refreshRest(
        newHour: Int,
        newMinute: Int,
        finishEventId: Long? = null
    ): Completable {
        return eventInteractor.getAllRaceEventListSingle(PreferenceManager().getCurrentRace())
            .doOnSuccess {
                val eventList = it.sortedBy { it.id }
                val restStartEvent = if (finishEventId == null)
                    eventList.findLast { it.raceEventType == RaceEventType.REST_START }!!
                else let {
                    for (i in eventList.indexOfFirst { it.id == finishEventId }..0) {
                        if (eventList[i].raceEventType == RaceEventType.REST_START) return@let eventList[i]
                    }
                    return@let eventList.first()
                }
                val oldHour = restStartEvent.hour.toInt()
                val oldMinute = restStartEvent.minute.toInt()
                val diffMinutes =
                    if (newHour < oldHour || (newHour == oldHour && newMinute < oldMinute)) {
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

}