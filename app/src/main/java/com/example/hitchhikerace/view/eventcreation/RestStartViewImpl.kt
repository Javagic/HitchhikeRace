package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.example.hitchhikerace.R
import com.example.hitchhikerace.data.database.RaceEventEntity
import com.example.hitchhikerace.data.database.RaceEventType
import com.example.hitchhikerace.view.BaseRaceEventView
import com.example.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_rest_start.*

class RestStartViewImpl : BaseRaceEventView() {

    override fun createRaceEventViewModel(): RaceEventViewModel {

        return RaceEventViewModel(
            if (isStart) RaceEventType.REST_START else RaceEventType.REST_FINISH,
            if (isStart) getString(R.string.rest_start) else getString(R.string.rest_finish),
            "",
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
        raceEvent?.run {
            timePicker.hours = hour.toInt()
            timePicker.minutes = minute.toInt()
        }
    }

    companion object {
        fun instance(raceEntity: RaceEventEntity?, isStart: Boolean): RestStartViewImpl {
            return RestStartViewImpl().apply {
                arguments = bundleOf("start" to isStart, "raceEvent" to raceEntity)
            }
        }
    }
}