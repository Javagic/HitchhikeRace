package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.database.SHIFT
import com.example.hitchhikerace.view.BaseRaceEventCreatorView
import com.example.hitchhikerace.view.PreferenceManager
import com.example.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_take_checkpoint.*
import java.util.*

class TakeCheckPointViewImpl : BaseRaceEventCreatorView() {

    override fun getLayoutId(): Int = R.layout.screen_take_checkpoint

    override fun initView(view: View, savedInstanceState: Bundle?) {
        timePicker.init()
        timePicker1.init()
        timePicker2.init()
        timePicker3.init()
        val arrayAdapter =
            ArrayAdapter(
                view.context,
                R.layout.spinner_item_default,
                PreferenceManager().getCrewList().toMutableList().apply { add(0, "") }
            )
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown_default)
        spinnerCrew1.adapter = arrayAdapter
        spinnerCrew2.adapter = arrayAdapter
        spinnerCrew3.adapter = arrayAdapter
    }

    override fun createRaceEventViewModel(): RaceEventViewModel = RaceEventViewModel(
        RaceEventType.TAKE_CHECKPOINT,
        createMainText(),
        etEventDescription.text.toString(),
        timePicker.hours.toString(),
        timePicker.minutes.toString(),
        getCurrentLocation().latitude,
        getCurrentLocation().longitude
    )

    private fun createMainText(): String {
        var result = etPointName.text.toString().toUpperCase(Locale.getDefault())
        if (spinnerCrew1.selectedItem != "") {
            result += SHIFT + " ${spinnerCrew1.selectedItem}-${timePicker1.hours}:${timePicker1.minutes}"
        }
        if (spinnerCrew2.selectedItem != "") {
            result += SHIFT + " ${spinnerCrew2.selectedItem}-${timePicker2.hours}:${timePicker2.minutes}"
        }
        if (spinnerCrew3.selectedItem != "") {
            result += SHIFT + " ${spinnerCrew3.selectedItem}-${timePicker3.hours}:${timePicker3.minutes}"
        }
        return result
    }

    override fun getEventTitle(): Int = R.string.take_checkpoint

}