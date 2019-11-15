package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.view.BaseRaceEventCreatorView
import com.example.hitchhikerace.view.PreferenceManager
import com.example.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_crew_meating.*

class CrewMeatingViewImpl : BaseRaceEventCreatorView() {

    override fun getLayoutId(): Int = R.layout.screen_crew_meating

    override fun initView(view: View, savedInstanceState: Bundle?) {
        timePicker.init()
        val arrayAdapter =
            ArrayAdapter(
                view.context,
                R.layout.spinner_item_default,
                PreferenceManager().getCrewList().toMutableList().apply { add(0, "") }
            )
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_default)
        spinnerCrew.adapter = arrayAdapter
    }

    override fun createRaceEventViewModel(): RaceEventViewModel = RaceEventViewModel(
        RaceEventType.CREW_MEATING,
        "${spinnerCrew.selectedItem} ${etMainText.text}",
        "",
        timePicker.hours.toString(),
        timePicker.minutes.toString(),
        getCurrentLocation().latitude,
        getCurrentLocation().longitude
    )

    override fun getEventTitle(): Int = R.string.crew_meating

}