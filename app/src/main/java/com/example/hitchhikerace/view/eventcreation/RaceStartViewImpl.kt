package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.view.BaseRaceEventCreatorView
import com.example.hitchhikerace.view.PreferenceManager
import com.example.hitchhikerace.view.RaceEventViewModel
import com.example.hitchhikerace.view.RestEntity
import kotlinx.android.synthetic.main.screen_race_start_finish.*

class RaceStartViewImpl : BaseRaceEventCreatorView() {
    override fun getLayoutId() = R.layout.screen_race_start_finish

    override fun initView(view: View, savedInstanceState: Bundle?) {
        timePicker.init()
    }

    override fun createRaceEventViewModel(): RaceEventViewModel {
        etRestCurrent.text.split(" ")
            .let { RestEntity(it) }
            .let { PreferenceManager().saveCurrentRest(it) }
        return RaceEventViewModel(
            RaceEventType.RACE_START,
            getString(R.string.race_start_title),
            "",
            timePicker.hours.toString(),
            timePicker.minutes.toString(),
            getCurrentLocation().latitude,
            getCurrentLocation().longitude
        )
    }


    override fun getEventTitle() = R.string.race_start_title

}