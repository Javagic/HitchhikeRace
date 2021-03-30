package me.javagic.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.data.pojo.RaceEventType
import me.javagic.hitchhikerace.view.fragments.base.BaseRaceEventView
import me.javagic.hitchhikerace.data.PreferenceManager
import me.javagic.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_crew_meeting.*

class CrewMeetingViewImpl : BaseRaceEventView() {

    override fun getLayoutId(): Int = R.layout.screen_crew_meeting

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
        raceEvent?.run {
            etMainText.setText(mainText)
            //TODO test variations of crew naming
            spinnerCrew.setSelection(PreferenceManager().getCrewList().indexOfFirst { it == specialDataText })
            timePicker.hours = hour.toInt()
            timePicker.minutes = minute.toInt()
        }
    }

    override fun createRaceEventViewModel(): RaceEventViewModel = RaceEventViewModel(
        RaceEventType.CREW_MEETING,
        "${etMainText.text}",
        "${spinnerCrew.selectedItem}",
        "",
        timePicker.hours.toString(),
        timePicker.minutes.toString(),
        getCurrentLocation().latitude,
        getCurrentLocation().longitude
    )

    override fun getEventTitle(): Int = R.string.crew_meeting

    companion object {
        fun instance(raceEntity: RaceEventEntity?): CrewMeetingViewImpl {
            return CrewMeetingViewImpl().apply {
                arguments = bundleOf("raceEvent" to raceEntity)
            }
        }
    }
}