package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.example.hitchhikerace.R
import com.example.hitchhikerace.data.database.RaceEventEntity
import com.example.hitchhikerace.data.database.RaceEventType
import com.example.hitchhikerace.view.BaseRaceEventView
import com.example.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_orientation_start_finish.*

class OrientationStartViewImpl : BaseRaceEventView() {

    override fun getLayoutId(): Int = R.layout.screen_orientation_start_finish

    override fun initView(view: View, savedInstanceState: Bundle?) {
        timePicker.init()
        raceEvent?.run {
            timePicker.hours = hour.toInt()
            timePicker.minutes = minute.toInt()
        }
    }

    override fun createRaceEventViewModel(): RaceEventViewModel = RaceEventViewModel(
        if (isStart) RaceEventType.ORIENTATION_START else RaceEventType.ORIENTATION_FINISH,
        if (isStart) getString(R.string.orientation_start) else getString(R.string.orientation_finish),
        "",
        "",
        timePicker.hours.toString(),
        timePicker.minutes.toString(),
        getCurrentLocation().latitude,
        getCurrentLocation().longitude
    )

    override fun getEventTitle(): Int =
        if (isStart) R.string.orientation_start else R.string.orientation_finish

    companion object {
        fun instance(raceEntity: RaceEventEntity?, isStart: Boolean): OrientationStartViewImpl {
            return OrientationStartViewImpl().apply {
                arguments = bundleOf("start" to isStart, "raceEvent" to raceEntity)
            }
        }
    }
}