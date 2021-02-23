package com.example.hitchhikerace.view.eventcreation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TimePicker
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.os.bundleOf
import com.example.hitchhikerace.R
import com.example.hitchhikerace.data.database.entity.RaceEventEntity
import com.example.hitchhikerace.data.pojo.RaceEventType
import com.example.hitchhikerace.utils.SHIFT
import com.example.hitchhikerace.view.fragments.base.BaseRaceEventView
import com.example.hitchhikerace.data.PreferenceManager
import com.example.hitchhikerace.view.RaceEventViewModel
import kotlinx.android.synthetic.main.screen_take_checkpoint.*

class TakeCheckPointViewImpl : BaseRaceEventView() {

    override fun getLayoutId(): Int = R.layout.screen_take_checkpoint

    override fun validateInput(): String {
        return if (spinnerCheckPoint.selectedItem == "") getString(R.string.warning_checkpoint) else ""
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        timePicker.init()
        timePicker1.init()
        timePicker2.init()
        timePicker3.init()
        val crewAdapter =
            ArrayAdapter(
                view.context,
                R.layout.spinner_item_default,
                PreferenceManager().getCrewList().toMutableList().apply { add(0, "") }
            )
        val checkPointAdapter =
            ArrayAdapter(
                view.context,
                R.layout.spinner_item_default,
                PreferenceManager().getCheckPointList().toMutableList().apply { add(0, "") }
            )
        crewAdapter.setDropDownViewResource(R.layout.spinner_dropdown_default)
        spinnerCrew1.adapter = crewAdapter
        spinnerCrew2.adapter = crewAdapter
        spinnerCrew3.adapter = crewAdapter
        spinnerCheckPoint.adapter = checkPointAdapter
        raceEvent?.run {
            val dataList = specialDataText.split(SHIFT)
            spinnerCheckPoint.setSelection(PreferenceManager().getCheckPointList().indexOfFirst { it == dataList[0] } + 1)
            dataList.getOrNull(1)?.let {
                initCrew(it, spinnerCrew1, timePicker1)
            }
            dataList.getOrNull(2)?.let {
                initCrew(it, spinnerCrew2, timePicker2)
            }
            dataList.getOrNull(3)?.let {
                initCrew(it, spinnerCrew3, timePicker3)
            }
            etEventDescription.setText(eventDescription)
            timePicker.hours = hour.toInt()
            timePicker.minutes = minute.toInt()
        }
    }

    private fun initCrew(
        dataList: String,
        spinner: AppCompatSpinner,
        timePicker: TimePicker
    ) {
        val data = dataList.split(" ")
        spinner.setSelection(PreferenceManager().getCrewList().indexOfFirst { it == data[0] }+ 1)
        timePicker.hours = data[1].split(":")[0].toInt()
        timePicker.minutes = data[1].split(":")[1].toInt()
    }

    override fun createRaceEventViewModel(): RaceEventViewModel = RaceEventViewModel(
        RaceEventType.TAKE_CHECKPOINT,
        "",
        createMainText(),
        etEventDescription.text.toString(),
        timePicker.hours.toString(),
        timePicker.minutes.toString(),
        getCurrentLocation().latitude,
        getCurrentLocation().longitude
    )

    private fun createMainText(): String {
        var result = ""
        if (spinnerCheckPoint.selectedItem != "") {
            result += "${spinnerCheckPoint.selectedItem}"
        }
        if (spinnerCrew1.selectedItem != "") {
            result += SHIFT + "${spinnerCrew1.selectedItem} ${timePicker1.hours}:${timePicker1.minutes}"
        }
        if (spinnerCrew2.selectedItem != "") {
            result += SHIFT + "${spinnerCrew2.selectedItem} ${timePicker2.hours}:${timePicker2.minutes}"
        }
        if (spinnerCrew3.selectedItem != "") {
            result += SHIFT + "${spinnerCrew3.selectedItem} ${timePicker3.hours}:${timePicker3.minutes}"
        }
        return result
    }

    override fun getEventTitle(): Int = R.string.take_checkpoint

    companion object {
        fun instance(raceEntity: RaceEventEntity?): TakeCheckPointViewImpl {
            return TakeCheckPointViewImpl().apply {
                arguments = bundleOf("raceEvent" to raceEntity)
            }
        }
    }
}