package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.example.hitchhikerace.R
import com.example.hitchhikerace.data.database.RaceEventEntity
import com.example.hitchhikerace.data.database.RaceEventType
import com.example.hitchhikerace.utils.showKeyboard
import com.example.hitchhikerace.view.BaseRaceEventView
import com.example.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_car_start.*

class CarStartViewImpl : BaseRaceEventView() {

    override fun getLayoutId() = R.layout.screen_car_start

    override fun getEventTitle() =
        if (isStart) R.string.car_start_title else R.string.car_finish_title

    override fun createRaceEventViewModel(): RaceEventViewModel {
        val location = getCurrentLocation()
        return RaceEventViewModel(
            if (isStart) RaceEventType.CAR_START else RaceEventType.CAR_FINISH,
            etMainText.text.toString(),
            "",
            etEventDescription.text.toString(),
            timePicker.hours.toString(),
            timePicker.minutes.toString(),
            location.latitude,
            location.longitude
        )
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        timePicker.init()
        etMainText.showKeyboard()
        tvMainTextTitle.text =
            if (isStart) view.context.getString(R.string.car_main_text_hint) else view.context.getString(
                R.string.car_finish_hint
            )
        raceEvent?.run {
            etMainText.setText(mainText)
            etEventDescription.setText(eventDescription)
            timePicker.hours = hour.toInt()
            timePicker.minutes = minute.toInt()
        }
    }

    companion object {
        fun instance(raceEntity: RaceEventEntity?, isStart: Boolean): CarStartViewImpl {
            return CarStartViewImpl().apply {
                arguments = bundleOf("start" to isStart, "raceEvent" to raceEntity)
            }
        }
    }
}