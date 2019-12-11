package com.example.hitchhikerace.view

import android.content.Context
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.RaceEventEntity
import com.example.hitchhikerace.database.RaceEventType

class EventTypeMapper {

    fun mapEventEntity(context: Context, list: List<RaceEventEntity>): String {
        return list.joinToString("\n") {
            context.getString(
                R.string.event_to_string,
                getEventTypeTitle(context, it.raceEventType),
                it.mainText,
                it.hour,
                it.minute
            ) ?: ""
        }
    }

    fun getEventTypeTitle(context: Context, type: RaceEventType) = when (type) {
        RaceEventType.START -> context.getString(R.string.event_type_car_start)
        RaceEventType.FINISH -> context.getString(R.string.event_type_car_start)
        RaceEventType.CAR_START -> context.getString(R.string.event_type_car_start)
        RaceEventType.CAR_FINISH -> context.getString(R.string.event_type_car_start)
        RaceEventType.RUN -> context.getString(R.string.event_type_car_start)
        RaceEventType.ORIENTATION -> context.getString(R.string.event_type_car_start)
        RaceEventType.TEAM_MEATING -> context.getString(R.string.event_type_car_start)
        RaceEventType.REST_START -> context.getString(R.string.event_type_car_start)
        RaceEventType.REST_FINISH -> context.getString(R.string.event_type_car_start)
        RaceEventType.ESCAPE -> context.getString(R.string.event_type_car_start)
        RaceEventType.CHECKPOINT -> context.getString(R.string.event_type_car_start)
        RaceEventType.CUSTOM -> context.getString(R.string.event_type_car_start)
    }
}