package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.view.BaseRaceEventCreatorView
import com.example.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_other.*

class OtherViewImpl : BaseRaceEventCreatorView() {

    override fun getLayoutId(): Int = R.layout.screen_other

    override fun initView(view: View, savedInstanceState: Bundle?) {
        timePicker.init()
    }

    override fun createRaceEventViewModel(): RaceEventViewModel = RaceEventViewModel(
        RaceEventType.CUSTOM,
        etMainText.text.toString(),
        "",
        timePicker.hours.toString(),
        timePicker.minutes.toString(),
        getCurrentLocation().latitude,
        getCurrentLocation().longitude
    )

    override fun getEventTitle(): Int = R.string.other

}