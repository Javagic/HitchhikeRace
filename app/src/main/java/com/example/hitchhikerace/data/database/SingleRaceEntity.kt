package com.example.hitchhikerace.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.hitchhikerace.data.database.SingleRaceEntity.Companion.TABLE_SINGLE_RACE

@Entity(tableName = TABLE_SINGLE_RACE)
class SingleRaceEntity(
    val name: String = "",
    val dateStart: String = "",
    val dateEnd: String = ""
) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    companion object {
        const val TABLE_SINGLE_RACE = "table_single_race"
    }

}