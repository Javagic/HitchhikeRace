package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.view.BaseRaceEventCreatorView
import com.example.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_run_start_finish.*

class RunStartViewImpl : BaseRaceEventCreatorView() {

    private val isStart by lazy {
        arguments?.get("start") == "start"
    }

    override fun getLayoutId(): Int = R.layout.screen_run_start_finish

    override fun initView(view: View, savedInstanceState: Bundle?) {
        timePicker.init()
    }

    override fun createRaceEventViewModel(): RaceEventViewModel = RaceEventViewModel(
        if (isStart) RaceEventType.RUN_START else RaceEventType.RUN_FINISH,
        if (isStart) getString(R.string.run_start) else getString(R.string.run_finish),
        "",
        timePicker.hours.toString(),
        timePicker.minutes.toString(),
        getCurrentLocation().latitude,
        getCurrentLocation().longitude
    )

    override fun getEventTitle(): Int = if (isStart) R.string.run_start else R.string.run_finish


}