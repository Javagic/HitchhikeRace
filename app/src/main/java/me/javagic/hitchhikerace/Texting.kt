package me.javagic.hitchhikerace

import me.javagic.hitchhikerace.data.database.entity.RaceEventEntity
import java.text.SimpleDateFormat
import java.util.*

object Texting {
    var dateFormat = "dd.MM HH:mm"

    fun technicalText(entity: RaceEventEntity?): String {
        entity ?: return ""
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        val realTimeText = SimpleDateFormat(dateFormat).format(calendar.time)
        return entity.hour + " " + entity.minute + " " + entity.mainText + " " + entity.eventDescription + " " + entity.specialDataText + "Изменено: " + realTimeText + "\n" + entity.technicalText
    }
}