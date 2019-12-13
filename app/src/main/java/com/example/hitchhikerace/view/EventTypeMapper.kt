package com.example.hitchhikerace.view

import android.content.Context
import com.example.hitchhikerace.R
import com.example.hitchhikerace.database.RaceEventEntity
import com.example.hitchhikerace.database.RaceEventType
import com.example.hitchhikerace.view.eventcreation.*

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

    fun getFeatureRaceEventFragment(
        context: Context,
        type: RaceEventType
    ): BaseRaceEventCreatorView = when (type) {
        RaceEventType.RACE_START -> RaceStartViewImpl()
        RaceEventType.RACE_FINISH -> RaceStartViewImpl()
        RaceEventType.CAR_START -> CarStartViewImpl()
        RaceEventType.CAR_FINISH -> CarStartViewImpl()
        RaceEventType.RUN_START -> RunStartViewImpl()
        RaceEventType.RUN_FINISH -> RunStartViewImpl()
        RaceEventType.ORIENTATION_START -> OrientationStartViewImpl()
        RaceEventType.ORIENTATION_FINISH -> OrientationStartViewImpl()
        RaceEventType.TEAM_MEATING -> TeamMeatingViewImpl()
        RaceEventType.REST_START -> RestStartViewImpl()
        RaceEventType.REST_FINISH -> RestStartViewImpl()
        RaceEventType.TAKE_CHECKPOINT -> TakeCheckPointViewImpl()
        RaceEventType.CUSTOM -> CustomEventViewImpl()
    }

    fun getEventTypeTitle(context: Context, type: RaceEventType) = when (type) {
        RaceEventType.RACE_START -> context.getString(R.string.event_type_car_start)
        RaceEventType.RACE_FINISH -> context.getString(R.string.event_type_car_start)
        RaceEventType.CAR_START -> context.getString(R.string.event_type_car_start)
        RaceEventType.CAR_FINISH -> context.getString(R.string.event_type_car_start)
        RaceEventType.RUN_START -> context.getString(R.string.event_type_car_start)
        RaceEventType.RUN_FINISH -> context.getString(R.string.event_type_car_start)
        RaceEventType.ORIENTATION_START -> context.getString(R.string.event_type_car_start)
        RaceEventType.ORIENTATION_FINISH -> context.getString(R.string.event_type_car_start)
        RaceEventType.TEAM_MEATING -> context.getString(R.string.event_type_car_start)
        RaceEventType.REST_START -> context.getString(R.string.event_type_car_start)
        RaceEventType.REST_FINISH -> context.getString(R.string.event_type_car_start)
        RaceEventType.TAKE_CHECKPOINT -> context.getString(R.string.event_type_car_start)
        RaceEventType.CUSTOM -> context.getString(R.string.event_type_car_start)
    }
}