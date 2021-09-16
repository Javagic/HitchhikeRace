package me.javagic.hitchhikerace

import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity

object Texting {
    fun technicalText(entity: RaceEventEntity?): String {
        entity ?: return ""
        return entity.hour + " " + entity.minute + " " + entity.mainText + " " + entity.eventDescription + " " + entity.specialDataText + "\n" + entity.technicalText
    }
}