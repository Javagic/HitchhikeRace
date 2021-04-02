package me.javagic.hitchhikerace.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import me.javagic.hitchhikerace.data.database.entity.SingleRaceEntity.Companion.TABLE_SINGLE_RACE

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