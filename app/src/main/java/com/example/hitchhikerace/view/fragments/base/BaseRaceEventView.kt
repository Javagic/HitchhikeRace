package com.example.hitchhikerace.view.fragments.base

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.widget.TimePicker
import com.example.hitchhikerace.utils.UserLocation
import com.example.hitchhikerace.data.database.entity.RaceEventEntity
import com.example.hitchhikerace.utils.tryOrNull
import com.example.hitchhikerace.view.RaceEventViewModel
import java.util.*

abstract class BaseRaceEventView : BaseFragment() {
    protected val isStart by lazy {
        arguments?.getBoolean("start") ?: false
    }

    protected val raceEvent by lazy {
        arguments?.get("raceEvent") as? RaceEventEntity
    }

    abstract fun createRaceEventViewModel(): RaceEventViewModel

    abstract fun getEventTitle(): Int

    open fun validateInput(): String = ""

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(): UserLocation = context?.let { context ->
        tryOrNull {
            (context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager)
                ?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?.let { UserLocation(it.latitude, it.longitude) }
        }
    } ?: UserLocation()

    protected fun TimePicker.init() {
        setIs24HourView(true)
        hours = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        minutes = Calendar.getInstance().get(Calendar.MINUTE)
    }

    protected var TimePicker.minutes: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) minute
        else currentMinute
        set(value) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) minute = value
            else currentMinute = value
        }

    protected var TimePicker.hours: Int
        get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) hour
        else currentHour
        set(value) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) hour = value
            else currentHour = value
        }
}