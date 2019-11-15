package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.view.BaseRaceEventCreatorView
import com.example.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_rest_start.*

class RestStartViewImpl : BaseRaceEventCreatorView() {

    private val isStart by lazy {
        arguments?.get("start") == "start"
    }

    override fun createRaceEventViewModel(): RaceEventViewModel {
        if (!isStart) {
        }
        return RaceEventViewModel(
            if (isStart) RaceEventType.REST_START else RaceEventType.REST_FINISH,
            if (isStart) getString(R.string.rest_start) else getString(R.string.rest_finish),
            "",
            timePicker.hours.toString(),
            timePicker.minutes.toString(),
            getCurrentLocation().latitude,
            getCurrentLocation().longitude
        )
    }

    override fun getLayoutId() = R.layout.screen_rest_start

    override fun getEventTitle() = if (isStart) R.string.rest_start else R.string.rest_finish

    override fun initView(view: View, savedInstanceState: Bundle?) {
        timePicker.init()
        tvTimeTitle.text =
            if (isStart) getString(R.string.time_rest_start) else getString(R.string.time_rest_finish)

    }

}