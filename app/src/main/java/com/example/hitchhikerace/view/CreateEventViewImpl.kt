package com.example.hitchhikerace.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.*
import kotlinx.android.synthetic.main.screen_create_event.*
import java.util.*
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE

class CreateEventViewImpl : Fragment() {

    private val eventType by lazy {
        arguments?.get("event_type") as RaceEventType
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.screen_create_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        timePicker.setIs24HourView(true)
        timePicker.hours = Calendar.getInstance().get(HOUR_OF_DAY)
        timePicker.minutes = Calendar.getInstance().get(MINUTE)
        context.showKeyboard()
        etMainText.requestFocus()
        btnCreateEvent.setOnClickListener {
            DataBaseInteractorImpl().addEvent(
                eventType,
                etMainText.text.toString(),
                etEventDescription.text.toString(),
                timePicker.hours.toString(),
                timePicker.minutes.toString()
            )
            close()
        }
        btnCancel.setOnClickListener {
            close()
        }
    }

    fun close() {
        activity.hideKeyboard()
        navController()?.popBackStack()
    }

    private var TimePicker.minutes: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) minute
        else currentMinute
        set(value) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) minute = value
            else currentMinute = value
        }

    private var TimePicker.hours: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) hour
        else currentHour
        set(value) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) hour = value
            else currentHour = value
        }
}