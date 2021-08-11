package me.javagic.hitchhikerace.view.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.screen_base_event.*
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.app.RaceApplication
import me.javagic.hitchhikerace.data.PreferenceManager
import me.javagic.hitchhikerace.data.RACE_EMPTY_ID
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.data.pojo.RaceEventType
import me.javagic.hitchhikerace.data.pojo.RestEntity
import me.javagic.hitchhikerace.domain.EventTypeMapper
import me.javagic.hitchhikerace.domain.RaceEventInteractor
import me.javagic.hitchhikerace.utils.isVisible
import me.javagic.hitchhikerace.utils.showToast
import me.javagic.hitchhikerace.utils.subscribeWithErrorLogConsumer
import me.javagic.hitchhikerace.view.fragments.base.BaseFragment
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
                RestHelper(eventInteractor, preferenceManager)
                    .refreshRest(
                        newEvent.hour.toInt(),
                        newEvent.minute.toInt(),
                        raceEvent?.id
                    )
                    .subscribeOn(Schedulers.io())
                    .subscribeWithErrorLogConsumer {
                        if (raceEvent?.id == null) {
                            eventInteractor.addEvent(raceEvent?.id ?: 0, newEvent)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    close()
                                    activity?.showToast()
                                }, {})
                        } else {
                            eventInteractor.insertAllRaceEventList(it)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    close()
                                    activity?.showToast()
                                }, {})
                        }
                    }
            } else if (eventType == RaceEventType.REST_START && raceEvent != null) {
                //начало реста менять не разрешаем
            } else {
                eventInteractor.addEvent(raceEvent?.id ?: 0, newEvent)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWithErrorLogConsumer {
                        if (eventType == RaceEventType.RACE_FINISH) {
                            preferenceManager.saveCurrentRace(RACE_EMPTY_ID)
                            preferenceManager.saveCurrentRest(RestEntity())
                            findNavController().setGraph(R.navigation.main_menu_navigation)
                            activity?.showToast()
                        } else {
                            close()
                            activity?.showToast()
                        }
                    }
            }
        }
        btnCancel.setOnClickListener {
            close()
        }
    }

}