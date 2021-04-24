package me.javagic.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import kotlinx.android.synthetic.main.screen_race_start_finish.*
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.data.PreferenceManager
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.data.pojo.RaceEventType
import me.javagic.hitchhikerace.data.pojo.RestEntity
import me.javagic.hitchhikerace.utils.isVisible
import me.javagic.hitchhikerace.utils.tryOrNull
import me.javagic.hitchhikerace.view.RaceEventViewModel
import me.javagic.hitchhikerace.view.fragments.base.BaseRaceEventView

class RaceStartViewImpl : BaseRaceEventView() {

    private var canGoNext = false

    override fun validateInput(): String {
        if (!isStart && !canGoNext) return getString(R.string.finish_race_confirmation)
        else if (isStart && crewNameText.text.isEmpty()) return getString(R.string.start_race_crew_name)
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
            crewNameText.isVisible = false
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
            PreferenceManager().saveMainCrewName(legendText.text.toString())
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