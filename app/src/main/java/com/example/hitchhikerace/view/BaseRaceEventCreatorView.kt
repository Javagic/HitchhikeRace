package com.example.hitchhikerace.view

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.hitchhikerace.UserLocation
import com.example.hitchhikerace.tryOrNull
import java.util.*

abstract class BaseRaceEventCreatorView : Fragment() {

    abstract fun createRaceEventViewModel(): RaceEventViewModel

    abstract fun getLayoutId(): Int

    abstract fun getEventTitle(): Int

    abstract fun initView(view: View, savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(getLayoutId(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view, savedInstanceState)
    }

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