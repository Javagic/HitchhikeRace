package com.example.hitchhikerace.view.raceinfo

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hitchhikerace.R
import com.example.hitchhikerace.app.RaceApplication
import com.example.hitchhikerace.domain.RaceEventInteractor
import com.example.hitchhikerace.utils.subscribeWithErrorLogConsumer
import com.example.hitchhikerace.view.BaseFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.screen_race_info.*
import javax.inject.Inject

class SingleRaceInfoViewImpl : BaseFragment() {

    @Inject
    lateinit var eventInteractor: RaceEventInteractor

    private val raceId by lazy { arguments?.getLong("raceId")!! }

    private val eventsAdapter = RaceEventsAdapter {
        findNavController().navigate(
            R.id.race_info_to_create_event,
            bundleOf("event_type" to it.raceEventType, "raceEvent" to it)
        )
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        RaceApplication.appComponent.inject(this)
        rvRaceEvents.apply {
            adapter = eventsAdapter
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
//            addItemDecoration(BaseItemDecoration(20))
        }
        eventInteractor.getAllRaceEventList(raceId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWithErrorLogConsumer {
                eventsAdapter.submitList(it.reversed())
            }
    }

    override fun getLayoutId(): Int = R.layout.screen_race_info

}