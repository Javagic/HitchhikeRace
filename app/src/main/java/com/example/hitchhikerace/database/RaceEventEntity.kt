package com.example.hitchhikerace.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.hitchhikerace.database.RaceEventEntity.Companion.TABLE_RACE_EVENT

@Entity(tableName = TABLE_RACE_EVENT)
@TypeConverters(Converters::class)
data class RaceEventEntity(
    var raceId: Long = 0,
    val raceEventType: RaceEventType,
    val eventDescription: String,
    val mainText: String,
    val hour: String,
    val minute: String,
    val realTime: Long
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        const val TABLE_RACE_EVENT = "table_race_event"
    }

}

object Converters {
    @JvmStatic
    @TypeConverter
    fun convertStringToListCondition(ordinal: Int): RaceEventType =
        RaceEventType.values()[ordinal]

    @JvmStatic
    @TypeConverter
    fun convertStringToListCondition(type: RaceEventType): Int = type.ordinal

}