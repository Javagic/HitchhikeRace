package com.example.hitchhikerace.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hitchhikerace.database.SingleRaceEntity.Companion.TABLE_SINGLE_RACE

@Entity(tableName = TABLE_SINGLE_RACE)
class SingleRaceEntity(name: String = "", dateStart: Long = -1, dateEnd: Long = -1) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        const val TABLE_SINGLE_RACE = "table_single_race"
    }

}