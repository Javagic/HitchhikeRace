package me.javagic.hitchhikerace.view.eventcreation.takecheckpoint

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.TimePicker
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.os.bundleOf
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.android.synthetic.main.screen_take_checkpoint.*
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.app.RaceApplication.Companion.mapper
import me.javagic.hitchhikerace.data.PreferenceManager
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.data.pojo.RaceEventType
import me.javagic.hitchhikerace.utils.dateFormat
import me.javagic.hitchhikerace.utils.dayMonthFormat
import me.javagic.hitchhikerace.utils.showDatePickerDialogOnClick
import me.javagic.hitchhikerace.view.RaceEventViewModel
import me.javagic.hitchhikerace.view.fragments.base.BaseRaceEventView
import java.util.*

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
        etDate1.initDatePicker()
        etDate2.initDatePicker()
        etDate3.initDatePicker()
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
            val crewList = mapper.readValue<List<CheckPointCrew>>(specialDataText)
            spinnerCheckPoint.setSelection(
                PreferenceManager().getCheckPointList()
                    .indexOfFirst { it == raceEvent?.mainText } + 1)
            crewList.getOrNull(0)?.let {
                initCrew(it, spinnerCrew1, timePicker1, etDate1)
            }
            crewList.getOrNull(1)?.let {
                initCrew(it, spinnerCrew2, timePicker2, etDate2)
            }
            crewList.getOrNull(2)?.let {
                initCrew(it, spinnerCrew3, timePicker3, etDate3)
            }
            etEventDescription.setText(eventDescription)
            timePicker.hours = hour.toInt()
            timePicker.minutes = minute.toInt()
        }
    }

    private fun initCrew(
        crew: CheckPointCrew,
        spinner: AppCompatSpinner,
        timePicker: TimePicker,
        etDate: EditText
    ) {
        spinner.setSelection(
            PreferenceManager().getCrewList().indexOfFirst { it == crew.crewName } + 1)
        timePicker.hours = crew.timeHour
        timePicker.minutes = crew.timeMinute
        etDate.setText(crew.date)
    }

    override fun createRaceEventViewModel(): RaceEventViewModel = RaceEventViewModel(
        RaceEventType.TAKE_CHECKPOINT,
        createMainText(),
        createSpecialText(),
        etEventDescription.text.toString(),
        timePicker.hours.toString(),
        timePicker.minutes.toString(),
        getCurrentLocation().latitude,
        getCurrentLocation().longitude
    )

    private fun createMainText(): String {
        return spinnerCheckPoint.selectedItem.toString()
    }

    private fun createSpecialText(): String {
        val result = mutableListOf<CheckPointCrew>()
        if (spinnerCrew1.selectedItem != "") {
            result.add(
                CheckPointCrew(
                    spinnerCrew1.selectedItem.toString(),
                    timePicker1.hours,
                    timePicker1.minutes,
                    etDate1.text.toString()
                )
            )
        }
        if (spinnerCrew2.selectedItem != "") {
            result.add(
                CheckPointCrew(
                    spinnerCrew2.selectedItem.toString(),
                    timePicker2.hours,
                    timePicker2.minutes,
                    etDate2.text.toString()
                )
            )
        }
        if (spinnerCrew3.selectedItem != "") {
            result.add(
                CheckPointCrew(
                    spinnerCrew3.selectedItem.toString(),
                    timePicker3.hours,
                    timePicker3.minutes,
                    etDate3.text.toString()
                )
            )
        }
        return mapper.writeValueAsString(result)
    }

    private fun EditText.initDatePicker() {
        context?.dayMonthFormat?.format(Calendar.getInstance().time)?.let {
            setText(it)
        }
        activity?.let {
            showDatePickerDialogOnClick(it, ::dateSetListener)
        }
    }

    private fun dateSetListener(date: Date, editText: EditText) {
        context?.dateFormat?.format(date)?.let {
            editText.setText(it)
        }
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