package me.javagic.hitchhikerace.domain

import android.content.Context
import android.location.Location
import me.javagic.hitchhikerace.R
import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import me.javagic.hitchhikerace.data.pojo.RaceEventType
import me.javagic.hitchhikerace.utils.SHIFT
import me.javagic.hitchhikerace.utils.tryOrNull
import me.javagic.hitchhikerace.view.eventcreation.*
import me.javagic.hitchhikerace.view.eventcreation.takecheckpoint.TakeCheckPointViewImpl
import me.javagic.hitchhikerace.view.fragments.base.BaseRaceEventView

class EventTypeMapper {
    var i = 1
    fun mapEventEntity(context: Context, list: List<RaceEventEntity>): String {
        val result = list.joinToString("\n") {
            eventToString(context, it)
        }
        val dt = tryOrNull {
            ""
//            return@tryOrNull SHIFT + "Пройдено пешком " + "690м."
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

    private fun additionalInfo(eventType: RaceEventEntity): String {
        return if (eventType.eventDescription.isNotEmpty()) {
            "$SHIFT ${eventType.eventDescription}"
        } else ""
    }

    fun eventToString(context: Context, eventType: RaceEventEntity): String = context.run {
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
            return "-----------------$SHIFT" + "Взятие точки ${eventType.mainText}: " + SHIFT +
                    eventType.timeString + SHIFT + " " + restString(
                eventType
            )  + SHIFT + eventType.takeCheckpointText() + SHIFT + "-----------------"
        }
        if (eventType.raceEventType == RaceEventType.REST_START) {
            return "Рест взят " + eventType.timeString
        }
        if (eventType.raceEventType == RaceEventType.REST_FINISH) {
            return "Рест снят " + eventType.timeString + SHIFT + restString(eventType)
        }
        if (eventType.raceEventType == RaceEventType.CREW_MEETING) {
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
        type: RaceEventType,
        event: RaceEventEntity?
    ): BaseRaceEventView = when (type) {
        RaceEventType.RACE_START -> RaceStartViewImpl.instance(event, true)
        RaceEventType.RACE_FINISH -> RaceStartViewImpl.instance(event, false)
        RaceEventType.CAR_START -> CarStartViewImpl.instance(event, true)
        RaceEventType.CAR_FINISH -> CarStartViewImpl.instance(event, false)
        RaceEventType.RUN_START -> RunStartViewImpl.instance(event, true)
        RaceEventType.RUN_FINISH -> RunStartViewImpl.instance(event, false)
        RaceEventType.ORIENTATION_START -> OrientationStartViewImpl.instance(event, true)
        RaceEventType.ORIENTATION_FINISH -> OrientationStartViewImpl.instance(event, false)
        RaceEventType.CREW_MEETING -> CrewMeetingViewImpl.instance(event)
        RaceEventType.REST_START -> RestStartViewImpl.instance(event, true)
        RaceEventType.REST_FINISH -> RestStartViewImpl.instance(event, false)
        RaceEventType.TAKE_CHECKPOINT -> TakeCheckPointViewImpl.instance(event)
        RaceEventType.CUSTOM -> OtherViewImpl.instance(event)
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
        RaceEventType.CREW_MEETING -> context.getString(R.string.crew_meeting)
        RaceEventType.REST_START -> context.getString(R.string.rest_start)
        RaceEventType.REST_FINISH -> context.getString(R.string.rest_finish)
        RaceEventType.TAKE_CHECKPOINT -> context.getString(R.string.take_checkpoint)
        RaceEventType.CUSTOM -> context.getString(R.string.other)
    }
}