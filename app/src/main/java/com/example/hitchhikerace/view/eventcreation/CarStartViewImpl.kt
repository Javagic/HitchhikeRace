package com.example.hitchhikerace.view.eventcreation

import android.Manifest
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
        tvMainTextTitle.text = view.context.getString(R.string.car_finish_hint)
    }

    companion object {
        const val PERMISSION_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        const val PERMISSION_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    }
}