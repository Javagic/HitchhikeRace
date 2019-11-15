package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.database.showKeyboard
import com.example.hitchhikerace.view.BaseRaceEventCreatorView
import com.example.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_car_start.*

class CarStartViewImpl : BaseRaceEventCreatorView() {

    private val isStart by lazy {
        arguments?.get("start") == "start"
    }

    override fun getLayoutId() = R.layout.screen_car_start

    override fun getEventTitle() =
        if (isStart) R.string.car_start_title else R.string.car_finish_title

    override fun createRaceEventViewModel(): RaceEventViewModel {
        val location = getCurrentLocation()
        return RaceEventViewModel(
            if (isStart) RaceEventType.CAR_START else RaceEventType.CAR_FINISH,
            etMainText.text.toString(),
            etEventDescription.text.toString(),
            timePicker.hours.toString(),
            timePicker.minutes.toString(),
            location.latitude,
            location.longitude
        )
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        timePicker.init()
        context.showKeyboard()
        etMainText.requestFocus()
        tvMainTextTitle.text =
            if (isStart) view.context.getString(R.string.car_main_text_hint) else view.context.getString(
                R.string.car_finish_hint
            )
    }

}