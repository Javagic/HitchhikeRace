package com.example.hitchhikerace.view.eventcreation

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.database.showKeyboard
import com.example.hitchhikerace.view.BaseRaceEventCreatorView
import com.example.hitchhikerace.view.PreferenceManager
import com.example.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_car_start.*

class CarStartViewImpl : BaseRaceEventCreatorView() {
    override fun getLayoutId() = R.layout.screen_car_start

    override fun getEventTitle() = R.string.car_start

    override fun createRaceEventViewModel(): RaceEventViewModel {
        val location = getCurrentLocation()
        return RaceEventViewModel(
            RaceEventType.CAR_START,
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
        val arrayAdapter =
            ArrayAdapter<String>(
                view.context,
                android.R.layout.simple_spinner_item,
                PreferenceManager().getCrewList()
            )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCrew.adapter = arrayAdapter
    }

    companion object {
        const val PERMISSION_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
        const val PERMISSION_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    }
}