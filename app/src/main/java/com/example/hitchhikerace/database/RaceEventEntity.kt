package com.example.hitchhikerace.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hitchhikerace.database.RaceEventEntity.Companion.TABLE_RACE_EVENT

@Entity(tableName = TABLE_RACE_EVENT)
class RaceEventEntity(
    var raceId: Long = 0,
    val raceEventType: RaceEventType,
    val alias: String,
    val date: Long,
    val realTime: Long
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        const val TABLE_RACE_EVENT = "table_race_event"
    }

}