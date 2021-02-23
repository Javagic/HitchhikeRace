package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.example.hitchhikerace.R
import com.example.hitchhikerace.data.database.entity.RaceEventEntity
import com.example.hitchhikerace.data.pojo.RaceEventType
import com.example.hitchhikerace.view.fragments.base.BaseRaceEventView
import com.example.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_run_start_finish.*

class RunStartViewImpl : BaseRaceEventView() {

    override fun getLayoutId(): Int = R.layout.screen_run_start_finish

    override fun initView(view: View, savedInstanceState: Bundle?) {
        timePicker.init()
        raceEvent?.run {
            timePicker.hours = hour.toInt()
            timePicker.minutes = minute.toInt()
        }
    }

    override fun createRaceEventViewModel(): RaceEventViewModel = RaceEventViewModel(
        if (isStart) RaceEventType.RUN_START else RaceEventType.RUN_FINISH,
        if (isStart) getString(R.string.run_start) else getString(R.string.run_finish),
        "",
        "",
        timePicker.hours.toString(),
        timePicker.minutes.toString(),
        getCurrentLocation().latitude,
        getCurrentLocation().longitude
    )

    override fun getEventTitle(): Int = if (isStart) R.string.run_start else R.string.run_finish

    companion object {
        fun instance(raceEntity: RaceEventEntity?, isStart: Boolean): RunStartViewImpl {
            return RunStartViewImpl().apply {
                arguments = bundleOf("start" to isStart, "raceEvent" to raceEntity)
            }
        }
    }
}