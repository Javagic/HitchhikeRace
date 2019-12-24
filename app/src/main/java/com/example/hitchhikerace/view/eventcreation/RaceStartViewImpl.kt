package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.example.hitchhikerace.R
import com.example.hitchhikerace.data.database.RaceEventEntity
import com.example.hitchhikerace.data.database.RaceEventType
import com.example.hitchhikerace.utils.isVisible
import com.example.hitchhikerace.utils.tryOrNull
import com.example.hitchhikerace.view.BaseRaceEventView
import com.example.hitchhikerace.view.PreferenceManager
import com.example.hitchhikerace.view.RaceEventViewModel
import com.example.hitchhikerace.view.RestEntity
import kotlinx.android.synthetic.main.screen_race_start_finish.*

class RaceStartViewImpl : BaseRaceEventView() {

    private var canGoNext = false

    override fun validateInput(): String {
        if (!isStart && !canGoNext) return getString(R.string.finish_race_confirmation)
        else if (!isStart) return ""
        return tryOrNull { RestEntity(etRestCurrent.text.trim().split(" ")) }?.let { "" }
            ?: getString(R.string.warning_race_start)
    }

    override fun getLayoutId() = R.layout.screen_race_start_finish

    override fun initView(view: View, savedInstanceState: Bundle?) {
        timePicker.init()
        if (!isStart) {
            tvTimeTitle.text = getString(R.string.race_finish_title)
            etRestCurrent.isVisible = false
            tvRestTitle.isVisible = false
            btnFinishRace.isVisible = true
            btnFinishRace.setOnLongClickListener {
                canGoNext = true
                btnFinishRace.text = getString(R.string.finish_race_confirmed)
                true
            }
        } else {
            btnFinishRace.isVisible = false
        }
        raceEvent?.run {
            timePicker.hours = hour.toInt()
            timePicker.minutes = minute.toInt()
            etRestCurrent.setText(RestEntity(currentRest.split(" ")).toString())
        }
    }

    override fun createRaceEventViewModel(): RaceEventViewModel {
        //TODO add checking for rest field!!!!
        if (isStart) {
            RestEntity(etRestCurrent.text.split(" "))
                .let { PreferenceManager().saveCurrentRest(it) }
            return RaceEventViewModel(
                RaceEventType.RACE_START,
                getString(R.string.race_start_title),
                "",
                "",
                timePicker.hours.toString(),
                timePicker.minutes.toString(),
                getCurrentLocation().latitude,
                getCurrentLocation().longitude
            )
        } else {
            return RaceEventViewModel(
                RaceEventType.RACE_FINISH,
                getString(R.string.race_finish_title),
                "",
                "",
                timePicker.hours.toString(),
                timePicker.minutes.toString(),
                getCurrentLocation().latitude,
                getCurrentLocation().longitude
            )
        }
    }

    override fun getEventTitle() =
        if (isStart) R.string.race_start_title else R.string.race_finish_title

    companion object {
        fun instance(raceEntity: RaceEventEntity?, isStart: Boolean): RaceStartViewImpl {
            return RaceStartViewImpl().apply {
                arguments = bundleOf("start" to isStart, "raceEvent" to raceEntity)
            }
        }
    }
}