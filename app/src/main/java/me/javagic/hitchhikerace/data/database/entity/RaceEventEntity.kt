package me.javagic.hitchhikerace.data.database.entity

import android.os.Parcelable
import androidx.room.*
import me.javagic.hitchhikerace.data.pojo.RaceEventType
import kotlinx.android.parcel.Parcelize

const val TABLE_RACE_EVENT = "table_race_event"

@Entity(tableName = TABLE_RACE_EVENT)
@TypeConverters(Converters::class)
@Parcelize
data class RaceEventEntity(
    var raceId: Long = 0,
    val raceEventType: RaceEventType,
    val eventDescription: String,
    val mainText: String,
    val specialDataText: String,
    val hour: String,
    val minute: String,
    val realTime: Long,
    val latitude: Double,
    val longitude: Double,
    val currentRest: String
) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    //TODO
    @Ignore
    val timeString =
        (if (hour.length < 2) "0" else "") + hour + ":" + (if (minute.length < 2) "0" else "") + minute

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