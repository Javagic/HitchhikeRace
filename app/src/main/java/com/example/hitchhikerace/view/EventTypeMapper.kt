package com.example.hitchhikerace.view

import android.content.Context
import android.location.Location
import androidx.core.os.bundleOf
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.RaceEventEntity
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.database.SHIFT
import com.example.hitchhikerace.tryOrNull
import com.example.hitchhikerace.view.eventcreation.*

class EventTypeMapper {
    var i = 1
    fun mapEventEntity(context: Context, list: List<RaceEventEntity>): String {
        val result = list.joinToString("\n") {
            context.eventToString(it)
        }
        val dt = tryOrNull {
            return@tryOrNull SHIFT + "Пройдено пешком " + "690м."
        }
        return result + (dt ?: "")

    }

    private fun RaceEventEntity.distance(other: RaceEventEntity): Float {
        return Location("locationA").apply {
            latitude = this@distance.latitude
            longitude = this@distance.longitude
        }.distanceTo(Location("locationA").apply {
            latitude = other.latitude
            longitude = other.longitude
        })
    }

    fun Context.restString(eventType: RaceEventEntity): String {
        return eventType.currentRest.split(" ").let {
            getString(R.string.rest_pattern, it[0].toInt(), it[1].toInt(), it[2].toInt())
        }
    }

    fun Context.additionalInfo(eventType: RaceEventEntity): String {
        return if (eventType.eventDescription.isNotEmpty()) {
            "$SHIFT ${eventType.eventDescription}"
        } else ""
    }

    fun Context.eventToString(eventType: RaceEventEntity): String {
        if (eventType.raceEventType == RaceEventType.RACE_START) {
            return "Старт " + eventType.timeString + SHIFT + restString(eventType)
        }
        if (eventType.raceEventType == RaceEventType.CAR_START) {
            return "${i++}). Посадка: " + eventType.timeString + " " + eventType.mainText + additionalInfo(
                eventType
            )
        }
        if (eventType.raceEventType == RaceEventType.CAR_FINISH) {
            return "Высадка: " + eventType.timeString + " " + eventType.mainText + additionalInfo(
                eventType
            )
        }
        if (eventType.raceEventType == RaceEventType.TAKE_CHECKPOINT) {
            return "Взятие точки: " + eventType.timeString + SHIFT + eventType.mainText + SHIFT + " " + restString(
                eventType
            ) + additionalInfo(
                eventType
            ) + SHIFT + "-----------------"
        }
        if (eventType.raceEventType == RaceEventType.REST_START) {
            return "Рест взят " + eventType.timeString
        }
        if (eventType.raceEventType == RaceEventType.REST_FINISH) {
            return "Рест снят " + eventType.timeString + SHIFT + restString(eventType)
        }
        if (eventType.raceEventType == RaceEventType.CREW_MEATING) {
            return "Экипаж " + eventType.mainText + " " + eventType.timeString
        }
        return if (eventType.raceEventType == RaceEventType.RACE_FINISH) {
            "Финиш " + eventType.timeString + SHIFT + "   " + restString(eventType)
        } else {
            return getEventTypeTitle(
                this,
                eventType.raceEventType
            ) + " " + (eventType.mainText.takeIf { it.isNotEmpty() }
                ?: "") + " " + eventType.timeString
        }
    }

    fun getFeatureRaceEventFragment(
        context: Context,
        type: RaceEventType
    ): BaseRaceEventCreatorView = when (type) {
        RaceEventType.RACE_START -> RaceStartViewImpl().apply {
            arguments = bundleOf("start" to "start")
        }
        RaceEventType.RACE_FINISH -> RaceStartViewImpl()
        RaceEventType.CAR_START -> CarStartViewImpl().apply {
            arguments = bundleOf("start" to "start")
        }
        RaceEventType.CAR_FINISH -> CarStartViewImpl()
        RaceEventType.RUN_START -> RunStartViewImpl().apply {
            arguments = bundleOf("start" to "start")
        }
        RaceEventType.RUN_FINISH -> RunStartViewImpl()
        RaceEventType.ORIENTATION_START -> OrientationStartViewImpl().apply {
            arguments = bundleOf("start" to "start")
        }
        RaceEventType.ORIENTATION_FINISH -> OrientationStartViewImpl()
        RaceEventType.CREW_MEATING -> CrewMeatingViewImpl()
        RaceEventType.REST_START -> RestStartViewImpl().apply {
            arguments = bundleOf("start" to "start")
        }
        RaceEventType.REST_FINISH -> RestStartViewImpl()
        RaceEventType.TAKE_CHECKPOINT -> TakeCheckPointViewImpl()
        RaceEventType.CUSTOM -> OtherViewImpl()
    }

    fun getEventTypeTitle(context: Context, type: RaceEventType) = when (type) {
        RaceEventType.RACE_START -> context.getString(R.string.race_start_title)
        RaceEventType.RACE_FINISH -> context.getString(R.string.race_finish_title)
        RaceEventType.CAR_START -> context.getString(R.string.car_start_title)
        RaceEventType.CAR_FINISH -> context.getString(R.string.car_finish_title)
        RaceEventType.RUN_START -> context.getString(R.string.run_start)
        RaceEventType.RUN_FINISH -> context.getString(R.string.run_finish)
        RaceEventType.ORIENTATION_START -> context.getString(R.string.orientation_start)
        RaceEventType.ORIENTATION_FINISH -> context.getString(R.string.orientation_finish)
        RaceEventType.CREW_MEATING -> context.getString(R.string.event_type_car_start)
        RaceEventType.REST_START -> context.getString(R.string.rest_start)
        RaceEventType.REST_FINISH -> context.getString(R.string.rest_finish)
        RaceEventType.TAKE_CHECKPOINT -> context.getString(R.string.take_checkpoint)
        RaceEventType.CUSTOM -> context.getString(R.string.other)
    }
}